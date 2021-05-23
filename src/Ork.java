public class Ork extends Zorde {
    private int HP = 200;

    public int getHP() {
        return HP;
    }

    public int getFullHP(){
        return 200;
    }

    public void setHP(int HP) {
        if (this.HP + HP >= 200) {
            this.HP =200;
        }else if(this.HP + HP <= 0){
            this.HP=0;
        }else {
            this.HP+=HP;
        }
    }

    public int getAP() {
        return Constants.orkAP;
    }

    public int getMaxMove() {
        return Constants.orkMaxMove;
    }

    public int getHealing() {
        return Constants.orkHealPoints;
    }

    //regulates and controls the movements of the character.
    //updates the situations caused by the character's movement.
    //This method is arranged separately for each character.
    public void move(Commands c,int[] moves, String lineFirstEle, Ork o){
        c.control=true;
        if (o.getName().equals(lineFirstEle) && o.getHP() > 0 && moves.length == Constants.orkMaxMove * 2) {
            int count = 0;
            for (int i = 0; i < moves.length; i += 2) {
                try {
                    if (c.getBoard()[o.getRow() + moves[i + 1]][o.getColumn() + moves[i]].equals("  ")) {
                        count++;
                        for (int row=-1;row<=1;row++) {
                            for (int column=-1;column<=1;column++) {
                                c.findZorde(o.getColumn() +column, o.getRow() +row).setHP(o.getHealing());
                            }
                        }
                        o.setXY(moves[i], moves[i + 1]);
                        if (count == Constants.orkMaxMove) {
                            for (int row=-1;row<=1;row++) {
                                for (int column=-1;column<=1;column++) {
                                    c.findCalliance(o.getColumn() +column, o.getRow() +row).setHP(-o.getAP());
                                }
                            }
                        }
                        c.setBoard();
                    } else if (c.getBoard()[o.getRow() + moves[i + 1]][o.getColumn() + moves[i]].charAt(0) == 'D' || c.getBoard()[o.getRow() + moves[i + 1]][o.getColumn() + moves[i]].charAt(0) == 'E' || c.getBoard()[o.getRow() + moves[i + 1]][o.getColumn() + moves[i]].charAt(0) == 'H') {
                        c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).setHP(-o.getAP());
                        if (c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).getHP() < o.getHP()) {
                            o.setHP(-c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).getHP());
                            c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).setHP(-o.getHP());
                            o.setXY(moves[i], moves[i + 1]);
                        } else if (c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).getHP() > o.getHP()) {
                            c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).setHP(-o.getHP());
                            o.setHP(-o.getHP());
                        } else {
                            o.setHP(-o.getHP());
                            c.findCalliance(o.getColumn() + moves[i], o.getRow() + moves[i + 1]).setHP(-o.getHP());
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
