public class Troll extends Zorde{
    private int HP = 150;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 150;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 150) {
            this.HP =150;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.trollAP;
    }

    public int getMaxMove() {
        return Constants.trollMaxMove;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c,int[] moves, String lineFirstEle, Troll t){
        c.control=true;
        if (t.getName().equals(lineFirstEle) && t.getHP() > 0 && moves.length == Constants.trollMaxMove * 2) {
            int count = 0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[t.getRow() + moves[i + 1]][t.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        t.setXY(moves[i], moves[i + 1]);
                        if (count == Constants.trollMaxMove) {
                            for (int row=-1;row<=1;row++) {
                                for (int column=-1;column<=1;column++) {
                                    c.findCalliance(t.getColumn() +column, t.getRow() +row).setHP(-t.getAP());
                                }
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[t.getRow() + moves[i + 1]][t.getColumn() + moves[i]].charAt(0) == 'D' || c.getBoard()[t.getRow() + moves[i + 1]][t.getColumn() + moves[i]].charAt(0) == 'E' || c.getBoard()[t.getRow() + moves[i + 1]][t.getColumn() + moves[i]].charAt(0) == 'H') {
                        c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).setHP(-t.getAP());
                        if (c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).getHP() < t.getHP()) {
                            t.setHP(-c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).getHP());
                            c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).setHP(-t.getHP());
                            t.setXY(moves[i], moves[i + 1]);
                        } else if (c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).getHP() > t.getHP()) {
                            c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).setHP(-t.getHP());
                            t.setHP(-t.getHP());
                        } else {
                            t.setHP(-t.getHP());
                            c.findCalliance(t.getColumn() + moves[i], t.getRow() + moves[i + 1]).setHP(-t.getHP());
                        }
                        c.setBoard();
                        break;
                    } else {
                        break;
                    }
                } catch (Exception de) {
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
