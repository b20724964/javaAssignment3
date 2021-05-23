public class Goblin extends Zorde{
    private int HP = 80;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 80;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 80) {
            this.HP =80;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.goblinAP;
    }

    public int getMaxMove() {
        return Constants.goblinMaxMove;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c, int[] moves, String lineFirstEle,Goblin g){
        c.control=true;
        if (g.getName().equals(lineFirstEle) && g.getHP() > 0 && moves.length == Constants.goblinMaxMove * 2) {
            int count=0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[g.getRow() + moves[i + 1]][g.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        g.setXY(moves[i], moves[i + 1]);
                        for (int row=-1;row<=1;row++) {
                            for (int column=-1;column<=1;column++) {
                                c.findCalliance(g.getColumn() +column, g.getRow() +row).setHP(-g.getAP());
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[g.getRow() + moves[i + 1]][g.getColumn() + moves[i]].charAt(0) == 'D' || c.getBoard()[g.getRow() + moves[i + 1]][g.getColumn() + moves[i]].charAt(0) == 'E' || c.getBoard()[g.getRow() + moves[i + 1]][g.getColumn() + moves[i]].charAt(0) == 'H') {
                        c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).setHP(-g.getAP());
                        if (c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).getHP() < g.getHP()) {
                            g.setHP(-c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).getHP());
                            c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).setHP(-g.getHP());
                            g.setXY(moves[i], moves[i + 1]);
                        } else if (c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).getHP() > g.getHP()) {
                            c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).setHP(-g.getHP());
                            g.setHP(-g.getHP());
                        } else {
                            g.setHP(-g.getHP());
                            c.findCalliance(g.getColumn() + moves[i], g.getRow() + moves[i + 1]).setHP(-g.getHP());
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
