public class Elf extends Calliance{
    private int HP = 70;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 70;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 70) {
            this.HP =70;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.elfAP;
    }

    public int getMaxMove() {
        return Constants.elfMaxMove;
    }

    public int getElfRangedAP() {
        return Constants.elfRangedAP;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c,int[] moves, String lineFirstEle, Elf e){
        c.control=true;
        if (e.getName().equals(lineFirstEle) && e.getHP() > 0 && moves.length == Constants.elfMaxMove * 2) {
            int count = 0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[e.getRow() + moves[i + 1]][e.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        e.setXY(moves[i], moves[i + 1]);
                        if (count == Constants.elfMaxMove) {
                            for (int row=-2;row<=2;row++) {
                                for (int column=-2;column<=2;column++) {
                                    c.findZorde(e.getColumn() +column, e.getRow() +row).setHP(-e.getElfRangedAP());
                                }
                            }
                        } else {
                            for (int row=-1;row<=1;row++) {
                                for (int column=-1;column<=1;column++) {
                                    c.findZorde(e.getColumn() +column, e.getRow() +row).setHP(-e.getAP());
                                }
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[e.getRow() + moves[i + 1]][e.getColumn() + moves[i]].charAt(0) == 'T' || c.getBoard()[e.getRow() + moves[i + 1]][e.getColumn() + moves[i]].charAt(0) == 'G' || c.getBoard()[e.getRow() + moves[i + 1]][e.getColumn() + moves[i]].charAt(0) == 'O') {
                        c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).setHP(-e.getAP());
                        if (c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).getHP() < e.getHP()) {
                            e.setHP(-c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).getHP());
                            c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).setHP(-e.getHP());
                            e.setXY(moves[i], moves[i + 1]);
                        } else if (c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).getHP() > e.getHP()) {
                            c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).setHP(-e.getHP());
                            e.setHP(-e.getHP());
                        } else {
                            e.setHP(-e.getHP());
                            c.findZorde(e.getColumn() + moves[i], e.getRow() + moves[i + 1]).setHP(-e.getHP());
                        }
                        c.setBoard();
                        break;
                    } else {
                        break;
                    }
                } catch (Exception ee) {
                    if(count>0){c.printBoard();}
                    c.writer.println("Error : Game board boundaries are exceeded. Input line ignored.\n");
                    c.control=false;
                    break;
                }
            }
        } else {
            try {
                c.control=false;
                throw new Exception("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
            } catch (Exception exception) {
                c.writer.println(exception.getMessage());
            }
            //c.writer.println("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
            //c.control=false;
        }
    }
}
