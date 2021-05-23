import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Commands implements Reader{
    private int boardLength=0;
    private String[][] board;
    private String[][] initials;
    public boolean control=true;
    public String[][] commands;
    public PrintWriter writer;

    //These two unlucky characters were created for the find(Zorde-Calliance) method.
    //because the method must return an object not return null.
    private Goblin unluckyGoblin=new Goblin();
    private Dwarf unluckyDwarf= new Dwarf();


    public ArrayList<Zorde> zordes=new ArrayList<Zorde>();
    public ArrayList<Ork> orks=new ArrayList<Ork>();
    public ArrayList<Troll> trolls=new ArrayList<Troll>();
    public ArrayList<Goblin> goblins=new ArrayList<Goblin>();
    public ArrayList<Calliance> calliances=new ArrayList<Calliance>();
    public ArrayList<Elf> elves=new ArrayList<Elf>();
    public ArrayList<Human> humans=new ArrayList<Human>();
    public ArrayList<Dwarf> dwarves=new ArrayList<Dwarf>();

    public Commands(String initials,String commands){
        this.initials=txtReader(initials);
        this.commands=txtReader(commands);
    }

    //Returns the character at the given point coordinate.
    public Zorde findZorde(int column, int row){
        for(Zorde z:this.zordes){
            if(z.getColumn()==column && z.getRow()==row){
                return z;
            }
        }return this.unluckyGoblin;

    }

    //Returns the character at the given point coordinate.
    public Calliance findCalliance(int column, int row){
        for(Calliance c:this.calliances){
            if(c.getColumn()==column && c.getRow()==row){
                return c;
            }
        }return this.unluckyDwarf;

    }

    public void createOutputFile(String output) {
        try {
            this.writer = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeOutputFile() {
        writer.close();
    }

    //It creates the game area according to the information given in initials.txt.
    public void createBoard(){
        this.board=new String[this.boardLength][this.boardLength];
    }

    //This method removes deceased characters from the playing field.
    //And updates the playing field after the movements of the characters.
    public void setBoard(){
        for(int i=0;i<this.board.length;i++){
            for(int j=0;j<board.length;j++){
                this.board[i][j]="  ";
            }
        }
        ArrayList<Zorde> newZordes=new ArrayList<>();
        for(Zorde z: this.zordes){
            if(z.getHP()>0){
                newZordes.add(z);
            }
        }
        this.zordes=newZordes;

        ArrayList<Calliance> newCalliance=new ArrayList<>();
        for(Calliance c: this.calliances){
            if(c.getHP()>0){
                newCalliance.add(c);
            }
        }
        this.calliances=newCalliance;

        for(Zorde z:this.zordes){
                this.board[z.getRow()][z.getColumn()]=z.getName();
        }
        for(Calliance c:this.calliances){
                this.board[c.getRow()][c.getColumn()]=c.getName();
        }
    }

    public String[][] getBoard(){
        return board;
    }

    //Checks for an error in the initials.txt file.
    //I wrote this method extra though not wanted. I noticed later on. I didn't want to delete after I wrote.
    public void checkInitial(){
        String[] boardSize;
        int callianceTitle=-1,zordeTitle=-1;
        for(int i=0;i< initials.length;i++){
            if(initials[i][0].equals("CALLIANCE")){
                callianceTitle=i;
            }else if(initials[i][0].equals("ZORDE")){
                zordeTitle=i;
            }
        }
        try{
            if(this.initials[0][0].equals("BOARD") && !this.initials[1][0].isEmpty()){
                boardSize=initials[1][0].split("x");
                Integer.parseInt(boardSize[0]);
                Integer.parseInt(boardSize[1]);

            }else{
                throw new Exception("Error: Initials.txt is not in the format it should be.");
            }
            if (callianceTitle!=-1 && zordeTitle!=-1){
                for(int i=callianceTitle+1;i<zordeTitle-1;i++){
                    if(!(initials[i][0].equals("ELF") || initials[i][0].equals("DWARF") || initials[i][0].equals("HUMAN"))){
                        throw new Exception("Error: Some character names are misspelled in Calliance in initial.txt.");
                    }
                    if(Integer.parseInt(initials[i][2])>=Integer.parseInt(boardSize[0]) || Integer.parseInt(initials[i][3])>=Integer.parseInt(boardSize[0])){
                        throw new Exception("Error: Some character position are wrong in Calliance in initial.txt.");
                    }
                }
                for(int i=zordeTitle+1;i<initials.length;i++){
                    if(!(initials[i][0].equals("GOBLIN") || initials[i][0].equals("TROLL") || initials[i][0].equals("ORK"))){
                        throw new Exception("Error: Some character names are misspelled in Zorde in initial.txt.");
                    }
                    if(Integer.parseInt(initials[i][2])>=Integer.parseInt(boardSize[0]) || Integer.parseInt(initials[i][3])>=Integer.parseInt(boardSize[0])){
                        throw new Exception("Error: Some character position are wrong in Zorde in initial.txt.");
                    }
                }

            }else{
                throw new Exception("Error: Initials.txt is not in the format it should be.");
            }
            if(!boardSize[0].equals(boardSize[1])) {
                throw new Exception("Error: Board must be square.");
            }
            if(Integer.parseInt(boardSize[0])<1 || Integer.parseInt(boardSize[1])<1){
                throw new NumberFormatException();
            }
            this.boardLength=Integer.parseInt(boardSize[0]);
            if(this.boardLength*this.boardLength< initials.length-6){
                throw new Exception("Error: The board is not big enough to hold all the characters.");
            }

        }catch (NumberFormatException e){
            writer.println("Error: The sides of the board must be positive numbers.");
            System.exit(0);
        }catch (Exception e){
            writer.println(e.getMessage());
            System.exit(0);
        }
    }

    //Separates the characters listed in initials.txt according to their group.
    public void createChars(){
        for(int i=0;i<initials.length;i++){
            if(initials[i][0].equals("ELF")){
                Elf elf=new Elf();
                elf.setName(initials[i][1]);
                elf.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.elves.add(elf);
                this.calliances.add(elf);
            }else if(initials[i][0].equals("DWARF")){
                Dwarf dwarf=new Dwarf();
                dwarf.setName(initials[i][1]);
                dwarf.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.dwarves.add(dwarf);
                this.calliances.add(dwarf);
            }else if(initials[i][0].equals("HUMAN")){
                Human human=new Human();
                human.setName(initials[i][1]);
                human.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.humans.add(human);
                this.calliances.add(human);
            }else if(initials[i][0].equals("GOBLIN")){
                Goblin goblin=new Goblin();
                goblin.setName(initials[i][1]);
                goblin.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.goblins.add(goblin);
                this.zordes.add(goblin);
            }else if(initials[i][0].equals("TROLL")){
                Troll troll=new Troll();
                troll.setName(initials[i][1]);
                troll.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.trolls.add(troll);
                this.zordes.add(troll);
            }else if(initials[i][0].equals("ORK")){
                Ork ork=new Ork();
                ork.setName(initials[i][1]);
                ork.setXYFirst(Integer.parseInt(initials[i][2]),Integer.parseInt(initials[i][3]));
                this.orks.add(ork);
                this.zordes.add(ork);
            }
        }
    }

    //draws "*" around the perimeter of the created playground.
    //prints the playing field.
    //prints the information of the characters in the game under the playing field.
    //checks if the game is over and prints out the winner accordingly.
    public void printBoard(){
        if(this.control){
            for(int i=0;i<board.length+1;i++){
                writer.print("**");
            }
            writer.println();
            for(String[] strings:board){
                writer.print("*");
                for(String s:strings){
                    writer.print(s);
                }
                writer.println("*");
            }
            for(int i=0;i<board.length+1;i++){
                writer.print("**");
            }
            writer.println("\n");
            Collections.sort(this.dwarves, Comparator.comparing(Dwarf::getName));
            for(Dwarf d:this.dwarves){
                if(d.getHP()>0){
                    writer.println(d.getName()+"\t"+d.getHP()+"\t"+"("+d.getFullHP()+")");
                }
            }
            Collections.sort(this.elves, Comparator.comparing(Elf::getName));
            for(Elf e:this.elves){
                if(e.getHP()>0){
                    writer.println(e.getName()+"\t"+e.getHP()+"\t"+"("+e.getFullHP()+")");
                }
            }
            Collections.sort(this.goblins,Comparator.comparing(Goblin::getName));
            for(Goblin g:this.goblins){
                if(g.getHP()>0){
                    writer.println(g.getName()+"\t"+g.getHP()+"\t"+"("+g.getFullHP()+")");
                }
            }
            Collections.sort(this.humans,Comparator.comparing(Human::getName));
            for(Human h:this.humans){
                if(h.getHP()>0){
                    writer.println(h.getName()+"\t"+h.getHP()+"\t"+"("+h.getFullHP()+")");
                }
            }
            Collections.sort(this.orks,Comparator.comparing(Ork::getName));
            for(Ork o:this.orks){
                if(o.getHP()>0){
                    writer.println(o.getName()+"\t"+o.getHP()+"\t"+"("+o.getFullHP()+")");
                }
            }
            Collections.sort(this.trolls,Comparator.comparing(Troll::getName));
            for(Troll t:this.trolls){
                if(t.getHP()>0){
                    writer.println(t.getName()+"\t"+t.getHP()+"\t"+"("+t.getFullHP()+")");
                }
            }
            writer.println();

            if(this.zordes.size()==0 && this.calliances.size()==0){
                writer.print("\nGame Finished\nDraw");
                writer.close();
                System.exit(0);
            }else if (this.zordes.size()==0){
                writer.print("\nGame Finished\nCalliance Wins");
                writer.close();
                System.exit(0);
            }else if (this.calliances.size()==0){
                writer.print("\nGame Finished\nZorde Wins");
                writer.close();
                System.exit(0);
            }
        }
    }

}
