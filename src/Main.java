public class Main {

    public static void main(String[] args) {

        Commands c = new Commands(args[0], args[1]);
        c.createOutputFile(args[2]);
        c.checkInitial();
        c.createChars();
        c.createBoard();
        c.setBoard();
        c.printBoard();

        //It performs operations on the commands array containing the information in the commands.txt file.
        try {
            for (String[] line : c.commands) {
                String[] movesString = line[1].split(";");
                int[] moves = new int[movesString.length];
                for (int k = 0; k < movesString.length; k++) {
                    moves[k] = Integer.parseInt(movesString[k]);
                }
                if (line[0].charAt(0) == 'D') {
                    for (Dwarf d : c.dwarves) {
                        if(d.getName().equals(line[0])){
                            d.move(c,moves,line[0],d);
                        }
                    }
                } else if (line[0].charAt(0) == 'H') {
                    for (Human h : c.humans) {
                        if(h.getName().equals(line[0])){
                            h.move(c,moves,line[0],h);
                        }
                    }
                } else if (line[0].charAt(0) == 'E') {
                    for (Elf e : c.elves) {
                        if(e.getName().equals(line[0])){
                            e.move(c,moves,line[0],e);
                        }
                    }
                } else if (line[0].charAt(0) == 'T') {
                    for (Troll t : c.trolls) {
                        if(t.getName().equals(line[0])){
                            t.move(c,moves,line[0],t);
                        }
                    }
                } else if (line[0].charAt(0) == 'G') {
                    for (Goblin g : c.goblins) {
                        if(g.getName().equals(line[0])){
                            g.move(c,moves,line[0],g);
                        }
                    }
                } else if (line[0].charAt(0) == 'O') {
                    for (Ork o : c.orks) {
                        if(o.getName().equals(line[0])){
                            o.move(c,moves,line[0],o);
                        }
                    }
                }
                c.printBoard();
            }
        } catch (Exception ae) {
            c.writer.println(ae);
        }
        c.closeOutputFile();
    }
}
