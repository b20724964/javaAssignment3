public class Human extends Calliance{
    private int HP = 100;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 100;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 100) {
            this.HP =100;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.humanAP;
    }

    public int getMaxMove() {
        return Constants.humanMaxMove;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c,int[] moves, String lineFirstEle, Human h){
        c.control=true;
        if (h.getName().equals(lineFirstEle) && h.getHP() > 0 && moves.length == Constants.humanMaxMove * 2) {
            int count = 0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[h.getRow() + moves[i + 1]][h.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        h.setXY(moves[i], moves[i + 1]);
                        if (count == Constants.humanMaxMove) {
                            for (int row=-1;row<=1;row++) {
                                for (int column=-1;column<=1;column++) {
                                    c.findZorde(h.getColumn() +column, h.getRow() +row).setHP(-h.getAP());
                                }
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[h.getRow() + moves[i + 1]][h.getColumn() + moves[i]].charAt(0) == 'T' || c.getBoard()[h.getRow() + moves[i + 1]][h.getColumn() + moves[i]].charAt(0) == 'G' || c.getBoard()[h.getRow() + moves[i + 1]][h.getColumn() + moves[i]].charAt(0) == 'O') {
                        c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).setHP(-h.getAP());
                        if (c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).getHP() < h.getHP()) {
                            h.setHP(-c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).getHP());
                            c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).setHP(-h.getHP());
                            h.setXY(moves[i], moves[i + 1]);
                        } else if (c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).getHP() > h.getHP()) {
                            c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).setHP(-h.getHP());
                            h.setHP(-h.getHP());
                        } else {
                            h.setHP(-h.getHP());
                            c.findZorde(h.getColumn() + moves[i], h.getRow() + moves[i + 1]).setHP(-h.getHP());
                        }
                        c.setBoard();
                        break;
                    } else {
                        break;
                    }
                } catch (Exception he) {
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
        }
    }
}
