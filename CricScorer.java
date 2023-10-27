import java.util.*;
import java.io.*;

class CricScorer {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Team> teams = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        teams = getTeamList();
        System.out.println("---------WelCome---------");
        int choice;
        do {
            System.out
                    .println("Press 1. To AddTeam\nPress 2. To Display Teams\nPress 3. To StartMatch\nPress 4. Exit");
            System.out.print("Enter : ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Team team1 = addTeam();
                    teams.add(team1);
                    break;
                case 2:
                    System.out.println("--------------------------------------------");
                    System.out.println("-----------------Show Teams-----------------");
                    System.out.println("--------------------------------------------");
                    getPlayerList1();
                    break;
                case 3: {
                    System.out.println("StartMatch");
                    ScoreBoard match = new ScoreBoard(1);
                    System.out.print("Enter Overs : ");
                    int over = sc.nextInt();
                    sc.nextLine();
                    match.setOverLimit(over);
                    System.out.println("For Team-1 (BatFirst)");
                    Team team3 = getTeam();
                    System.out.println("Selected Team-1 : " + team3.getName());
                    System.out.println("For Team-2");
                    Team team4 = getTeam();
                    System.out.println("Selected Team-2 : " + team4.getName());
                    match.setTeams(team3, team4);
                    match.startMatch();
                    System.out.println();
                    System.out.println();

                    System.out.println("------Second Innings-----");

                    ScoreBoard match1 = new ScoreBoard(2);
                    match1.setTeams(team4, team3);
                    match1.setOverLimit(over);
                    System.out.println(
                            team4.getName() + " need " + match.getTargetRun() + " runs in " + match1.getOverLimit()
                                    + " Overs");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    match1.setRemaingRun(match.getTargetRun());
                    match1.setRemaingBall(over);
                    match1.startMatch();
                    if (match1.getTotalRun() > match.getTotalRun()) {
                        System.out.println("--------------------------------------------");
                        System.out.println("Congratulations..." + team4.getName() + " WON !!");
                        System.out.println("--------------------------------------------");
                    } else if (match1.getTotalRun() < match.getTotalRun()) {
                        System.out.println("--------------------------------------------");
                        System.out.println("Congratulations..." + team3.getName() + " WON !!");
                        System.out.println("--------------------------------------------");
                    } else {
                        System.out.println("--------------------------------------------");
                        System.out.println("Match Drow !!");
                        System.out.println("--------------------------------------------");
                    }
                    break;
                }
                case 4:
                    System.out.println("thank you...(*_*)");
                    System.out.println(" ");
                    System.exit(0);
            }
        } while (choice != 4);

    }
    
    public static Team addTeam() throws IOException {
        ArrayList<Player> players = new ArrayList<>();
        BufferedWriter br = new BufferedWriter(
                new FileWriter("D:\\FinalCricScorer\\OriginalTeams.txt", true));
        String teamName = "";
        int t = 0;
        for (int i = 1; i < 12; i++) {
            if (t == 0) {
                System.out.print("Enter Team name : ");
                teamName = sc.nextLine();
                br.write(teamName + " : ");
                t = t + 1;
            }
            System.out.print("Enter Player-" + i + " Name : ");
            String n = sc.nextLine();
            br.write(n + " , ");
            Player player = new Player(n, teamName);
            players.add(player);
        }
        br.newLine();
        Team team = new Team(teamName, players);
        br.flush();
        br.close();
        return team;
    }

    public static Team getTeam() {
        boolean team_check = true;
        String team_name;
        int j;
        for (int i = 0; i < teams.size(); i++) {
            System.out.print(teams.get(i).getName() + "   ");
        }
        System.out.println("================");
        do {
            j = 0;
            System.out.print("Enter Team Name : ");
            team_name = sc.nextLine();
            while (j < (teams.size())) {
                if (team_name.equalsIgnoreCase(teams.get(j).getName())) {
                    team_check = false;
                    break;
                }
                j++;
            }
        } while (team_check);
        return teams.get(j);
    }

    public static ArrayList<Team> getTeamList() throws IOException {

        ArrayList<Team> list = new ArrayList<>();
        BufferedWriter br = new BufferedWriter(new FileWriter("D:\\FinalCricScorer\\OriginalTeams.txt"));
        BufferedReader rp = new BufferedReader(new FileReader("D:\\FinalCricScorer\\Team.txt"));

        String line1;
        while ((line1 = rp.readLine()) != null) {
            // System.out.println("Team name : " + line1);
            ArrayList<Player> players = new ArrayList<>();
            BufferedReader rt = new BufferedReader(
                    new FileReader("D:\\FinalCricScorer\\" + line1 + ".txt"));
            String line = rt.readLine();
            br.write(line1 + " : ");
            while (line != null) {
                // System.out.println("Player name : " + line);
                Player player = new Player(line, line1);
                players.add(player);
                br.write(line + " , ");
                line = rt.readLine();
            }
            br.newLine();
            rt.close();
            Team team = new Team(line1, players);
            list.add(team);
        }
        br.flush();
        br.close();
        rp.close();
        return list;
    }

    public static void getPlayerList1() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("D:\\FinalCricScorer\\OriginalTeams.txt"));
        String line = reader.readLine();
        while ((line) != null) {
            System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
    }
}

class Batsman {

    int run, ballPlayed, four, six;
    double strikeRate;
    boolean isOut;
    String outType;
    String outByBowler;
    String outByPlayer;
    Player player;

    public Batsman() {

    }

    public Batsman(Player player) {
        this.player = player;
    }

    public void setBatsman(Player player) {
        this.player = player;
    }

    public void setScore(int newRun) {
        this.run += newRun;
        this.increaseBall();
    }

    public void scoreFour() {
        this.run += 4;
        this.four++;
        this.increaseBall();
    }

    public void scoreSix() {
        this.run += 6;
        this.six++;
        this.increaseBall();
    }

    private void increaseBall() {
        this.ballPlayed++;
        this.strikeRate = ((double) this.run / this.ballPlayed) * 100;
    }

    public void runOutIncreaseBallBugFix() {
        this.ballPlayed--;
        this.strikeRate = ((double) this.run / this.ballPlayed) * 100;
    }

    public void setOut(boolean isStriker) {
        this.isOut = true;
        if (isStriker)
            this.increaseBall();
    }

    public void setOutInfo(String outType, String outByBowler) {
        this.outType = outType;
        this.outByBowler = outByBowler;
    }

    public void setOutInfo(String outType, String outByBowler, String outByPlayer) {
        this.outType = outType;
        this.outByBowler = outByBowler;
        this.outByPlayer = outByPlayer;
    }

    public String outInfo() {
        if (!this.isOut)
            return "Not Out";
        else {
            if (this.outType.equalsIgnoreCase("bowled"))
                return "b " + this.outByBowler + "  ";
            else if (this.outType.equalsIgnoreCase("LBW"))
                return "lbW b " + this.outByBowler;
            else if (this.outType.equalsIgnoreCase("Catch"))
                return "c " + this.outByPlayer + " b " + this.outByBowler;
            else if (this.outType.equalsIgnoreCase("Run Out"))
                return "run out (" + this.outByPlayer + ")";
            else if (this.outType.equalsIgnoreCase("Stump"))
                return "st " + this.outByPlayer + " b " + this.outByBowler;
            else
                return "Invalid";
        }
    }

    public int getRun() {
        return run;
    }

    public int getBallPlayed() {
        return ballPlayed;
    }

    public int getFour() {
        return four;
    }

    public int getSix() {
        return six;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public boolean isOut() {
        return isOut;
    }

    public String getOutType() {
        return outType;
    }

    public String getOutByBowler() {
        return outByBowler;
    }

    public String getOutByPlayer() {
        return outByPlayer;
    }

    public Player getPlayer() {
        return player;
    }
}

class Bowler {

    int ball;
    int run;
    String over;
    int maidenOver;
    int wickets;
    int wideBall;
    int noBall;
    double economy;
    Player player;

    public Bowler() {
    }

    public Bowler(Player player) {
        this.player = player;
    }

    public void setBowlers(Player player) {
        this.player = player;
    }

    private void increaseBall() {
        this.ball++;
        this.economy = this.run / (this.ball / 6.0);
    }

    private void decreaseBall() {
        this.ball--;
    }

    public void setRun(int run) {
        this.run += run;
        this.increaseBall();
    }

    public void noBall() {
        this.noBall++;
        try {
            this.economy = this.run / (this.ball / 6.0);
        } catch (NumberFormatException ignored) {
        }
    }

    public void wideBall() {
        this.wideBall++;
        this.run++;
        try {
            this.economy = this.run / (this.ball / 6.0);
        } catch (NumberFormatException ignored) {
        }
    }

    public void maidenOver() {
        this.maidenOver++;
    }

    public void takeWickets() {
        this.wickets++;
        this.increaseBall();
    }

    public int getBall() {
        return ball;
    }

    public int getRun() {
        return run;
    }

    public int getMaidenOver() {
        return maidenOver;
    }

    public int getWickets() {
        return wickets;
    }

    public int getWideBall() {
        return wideBall;
    }

    public int getNoBall() {
        return noBall;
    }

    public double getEconomy() {
        return economy;
    }

    public Player getPlayer() {
        return player;
    }

    public String getOver() {

        if (ball == 1)
            return "0.1";
        else if (ball == 2)
            return "0.2";
        else if (ball == 3)
            return "0.3";
        else if (ball == 4)
            return "0.4";
        else if (ball == 5)
            return "0.5";
        else {
            int over = ball / 6;
            int remBall = ball % 6;

            return over + "." + remBall;
        }
    }
}

class CurrentPlayer {

    Batsman striker;
    Batsman nonStriker;
    Bowler currentBowler;

    public CurrentPlayer() {
    }

    public CurrentPlayer(Batsman striker, Batsman nonStriker, Bowler currentBowler) {
        this.striker = striker;
        this.nonStriker = nonStriker;
        this.currentBowler = currentBowler;
    }

    public Batsman getStriker() {
        return striker;
    }

    public void setStriker(Batsman striker) {
        this.striker = striker;
    }

    public Batsman getNonStriker() {
        return nonStriker;
    }

    public void setNonStriker(Batsman nonStriker) {
        this.nonStriker = nonStriker;
    }

    public Bowler getCurrentBowlers() {
        return currentBowler;
    }

    public void setCurrentBowlers(Bowler currentBowler) {
        this.currentBowler = currentBowler;
    }
}

class Team {

    String name;
    ArrayList<Player> playersList;

    public Team() {
        this.playersList = new ArrayList<Player>();
    }

    public Team(String name) {
        this.name = name;
        this.playersList = new ArrayList<Player>();
    }

    public Team(String name, ArrayList<Player> playersList) {
        this.name = name;
        this.playersList = playersList;
    }

    public void addPlayer(Player player) {
        playersList.add(player);
    }

    public ArrayList<Player> getPlayersList() {
        ArrayList<Player> p = new ArrayList<>(playersList);
        return p;
    }

    public ArrayList<Player> getPlayersList1() {
        return playersList;
    }

    public String getName() {
        return name;
    }

}

class Player {

    String playerName;
    String team;

    Player() {

    }

    public Player(String playerName, String team) {
        this.playerName = playerName;
        this.team = team;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return team;
    }

}

class ScoreBoard {
    Scanner sc = new Scanner(System.in);
    String totalOver;
    int ID, totalRun, targetRun, extraRun, fallenWickets, totalBall, overLimit, wideBall, noBall, remaingBall,
            remaingRun;

    Team[] teams = new Team[2];
    CurrentPlayer currentPlayer;
    ArrayList<Batsman> batsmenListTeam1;
    ArrayList<Bowler> bowlerListTeam2;
    ArrayList<Player> team1Player;
    ArrayList<Player> team2Player;

    public ScoreBoard(int ID) {
        this.ID = ID;
        this.currentPlayer = new CurrentPlayer();
        this.batsmenListTeam1 = new ArrayList<Batsman>();
        this.bowlerListTeam2 = new ArrayList<Bowler>();
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }

    public void setTeams(Team team1, Team team2) {
        this.teams[0] = team1;
        this.teams[1] = team2;
        this.team1Player = new ArrayList<>(team1.getPlayersList());
        this.team2Player = new ArrayList<>(team2.getPlayersList());
    }

    public ArrayList<Player> getTeam1Players() {
        return team1Player;
    }

    public void updateBatsmenListTeam1(Batsman batsman) {
        this.batsmenListTeam1.add(batsman);
    }

    public void setRemaingBall(int b) {
        this.remaingBall = b * 6;
    }

    public void updateBowlersListTeam2(Bowler bowler) {
        this.bowlerListTeam2.add(bowler);
    }

    public void setScoreBoardStriker(Batsman batsman) {
        currentPlayer.setStriker(batsman);
    }

    public void setScoreBoardNonStriker(Batsman batsman) {
        currentPlayer.setNonStriker(batsman);
    }

    public void setScoreBoardBowlers(Bowler bowler) {
        currentPlayer.setCurrentBowlers(bowler);
    }

    public Batsman getScoreBoardStriker() {
        return currentPlayer.getStriker();
    }

    public Batsman getScoreBoardNonStriker() {
        return currentPlayer.getNonStriker();
    }

    public Bowler getScoreBoardBowlers() {
        return currentPlayer.getCurrentBowlers();
    }

    public int getReamaingBall() {
        return remaingBall;
    }

    public void setRemaingRun(int t) {
        this.remaingRun = t;
    }

    public void updateScore(int run) {

        if (run == 4)
            currentPlayer.getStriker().scoreFour();

        else if (run == 6)
            currentPlayer.getStriker().scoreSix();

        else
            currentPlayer.getStriker().setScore(run);

        currentPlayer.getCurrentBowlers().setRun(run);
        this.totalRun += run;
        this.totalBall++;
        this.remaingBall--;
        this.remaingRun -= run;
    }

    public void noBall() {

        currentPlayer.getCurrentBowlers().noBall();
        this.totalRun++;
        this.extraRun++;
        this.noBall++;
        this.remaingRun--;
    }

    public void wideBall() {

        currentPlayer.getCurrentBowlers().wideBall();
        this.totalRun++;
        this.extraRun++;
        this.wideBall++;
        this.remaingRun--;
    }

    public void switchBatsman() {

        Batsman tempBatsman = currentPlayer.getStriker();
        currentPlayer.setStriker(currentPlayer.getNonStriker());
        currentPlayer.setNonStriker(tempBatsman);
    }

    public void maidenOver1() {
        currentPlayer.getCurrentBowlers().maidenOver();
    }

    public void setScoreBoardCurrentBowler(Bowler bowler) {
        currentPlayer.setCurrentBowlers(bowler);
    }

    public void setBatsmanOut(Batsman batsman, boolean isStriker) {
        batsman.setOut(isStriker);
        this.fallenWickets++;
    }

    public void setBatsmanOutType(String outType, Batsman batsman) {
        batsman.setOutInfo(outType, currentPlayer.getCurrentBowlers().getPlayer().getPlayerName());
        currentPlayer.getCurrentBowlers().takeWickets();
        this.totalBall++;
        this.remaingBall--;
    }

    public void setBatsmanOutType(String outType, String outByPlayer, Batsman batsman) {
        batsman.setOutInfo(outType, currentPlayer.getCurrentBowlers().getPlayer().getPlayerName(), outByPlayer);
        if (!batsman.getOutType().equalsIgnoreCase("Run Out")) {
            currentPlayer.getCurrentBowlers().takeWickets();
            this.totalBall++;
            this.remaingBall--;
        } else if (batsman.getOutType().equals("Run Out") && batsman == currentPlayer.getStriker()) {
            batsman.runOutIncreaseBallBugFix();
            this.totalBall++;
            this.remaingBall--;
        }

    }

    public int getReamaingRun() {
        return remaingRun;
    }

    public void removePlayer(Player p) {
        getTeam1Players().remove(p);
    }

    public void startMatch() throws Exception {
        // first team is bowling team
        System.out.print("Enter Sriker Batsman Name : ");
        Player st_Player1 = getPlayer(team1Player);
        System.out.println("Selected Striker : " + st_Player1.getPlayerName());
        Batsman striker1 = new Batsman(st_Player1);
        setScoreBoardStriker(striker1);
        removePlayer(st_Player1);

        System.out.print("Enter Non-Sriker Batsman Name : ");
        Player stn_Player1 = getPlayer(team1Player);
        System.out.println("Selected Non-Striker : " + stn_Player1.getPlayerName());
        Batsman non_striker1 = new Batsman(stn_Player1);
        setScoreBoardNonStriker(non_striker1);
        removePlayer(stn_Player1);

        System.out.print("Enter Bowler Name : ");
        Player bowler1 = getPlayer(team2Player);
        System.out.println("Selected Bowler : " + bowler1.getPlayerName());
        Bowler s_bowler1 = new Bowler(bowler1);
        setScoreBoardBowlers(s_bowler1);

        scoreWriter(teams[0]);
        scoreRead();
        for (int i = 0; i < getOverLimit(); i++) {
            int ballCount = currentPlayer.getCurrentBowlers().getBall();
            int m = 0;
            int t2 = 0;
            int check1 = 0;
            while (ballCount != 5 & ballCount != 11 & ballCount != 17) {
                if (fallenWickets == 10) {
                    System.out.println("TEAM ALL OUT");
                    break;
                }
                if (getID() == 2 & getReamaingRun() > 0) {
                    System.out.println(remaingRun + " runs need in " + getReamaingBall() + " ball");
                }
                if (getReamaingRun() <= 0 & getID() == 2) {
                    t2 = 1;
                    break;
                }
                System.out.println("--------------------------------------------");
                System.out.println(
                        "Runs :   0   1   2   3   4   5   6\n7(WideBall) 8(NoBall) 9(Switch Batsman) 10(Wicket)");

                ballCount = currentPlayer.getCurrentBowlers().getBall();

                System.out.print("Enter : ");
                int run = sc.nextInt();
                if (run >= 0 && run < 7) {
                    if (run == 0) {
                        // System.out.println("maidan balls : " + m);
                        m++;
                    }
                    updateScore(run);
                    if (run == 1 || run == 3 || run == 5) {
                        switchBatsman();
                    }
                } else if (run == 7) {
                    wideBall();
                } else if (run == 8) {
                    noBall();
                } else if (run == 10) {
                    sc.nextLine();
                    currentPlayer.getCurrentBowlers().getWickets();
                    System.out.print("Name of Batsman : ");
                    System.out.print("Enter OutType(bowled,catch,lbw,run out,stump) : ");
                    String oType = sc.nextLine();
                    setBatsmanOut(currentPlayer.getStriker(), true);

                    if (oType.equalsIgnoreCase("bowled") || oType.equalsIgnoreCase("lbw")) {
                        setBatsmanOutType(oType, currentPlayer.getStriker());
                    } else {
                        System.out.print("Enter Out by Player : ");
                        Player outby = getPlayer(team2Player);
                        setBatsmanOutType(oType, outby.getPlayerName(), currentPlayer.getStriker());
                    }

                    updateBatsmenListTeam1(currentPlayer.getStriker());
                    Player newPlayer = getPlayer(team1Player);
                    System.out.println("Selected Player : " + newPlayer.getPlayerName());
                    Batsman newBatsman = new Batsman(newPlayer);
                    setScoreBoardStriker(newBatsman);
                    removePlayer(newPlayer);
                    m++;
                    if (fallenWickets == 10) {
                        System.out.println("TEAM ALL OUT");
                        break;
                    }

                } else if (run == 9) {
                    switchBatsman();
                } else {
                    System.out.println("Indvalid");
                }
                scoreWriter(teams[0]);
                scoreRead();
            }
            if (fallenWickets == 10) {
                System.out.println("TEAM ALL OUT");
                break;
            }
            if (m == 5 || m == 6) {
                maidenOver1();
            }
            for (int k = 0; k < bowlerListTeam2.size(); k++) {
                if (currentPlayer.getCurrentBowlers().getPlayer().getPlayerName()
                        .equalsIgnoreCase(bowlerListTeam2.get(k).getPlayer().getPlayerName())) {
                    check1 = 1;
                }
            }

            if (check1 != 1) {
                updateBowlersListTeam2(currentPlayer.getCurrentBowlers());
            }

            if (i != (getOverLimit() - 1) & getID() == 1) {
                int check = 0;
                switchBatsman();
                sc.nextLine();
                System.out.print("Enter Bowler Name : ");
                Player bowler2 = getPlayer(team2Player);
                for (int j = 0; j < bowlerListTeam2.size(); j++) {
                    if (bowlerListTeam2.get(j).getPlayer().getPlayerName().equals(bowler2.getPlayerName())) {
                        Bowler bowler3 = bowlerListTeam2.get(j);
                        check = 1;
                        setScoreBoardBowlers(bowler3);
                        System.out.println("Selected Bowler : " + bowler3.getPlayer().getPlayerName());
                        break;
                    }
                }
                if (check == 0) {
                    Bowler s_bowler2 = new Bowler(bowler2);
                    setScoreBoardBowlers(s_bowler2);
                    System.out.println("Selected Bowler : " + bowler2.getPlayerName());
                }

            }

            if (getID() == 2 & getReamaingRun() > 0 & i != (getOverLimit() - 1)) {
                int check = 0;
                switchBatsman();
                sc.nextLine();
                System.out.print("Enter Bowler Name : ");
                Player bowler2 = getPlayer(team2Player);
                for (int j = 0; j < bowlerListTeam2.size(); j++) {
                    if (bowlerListTeam2.get(j).getPlayer().getPlayerName().equals(bowler2.getPlayerName())) {
                        Bowler bowler3 = bowlerListTeam2.get(j);
                        check = 1;
                        setScoreBoardBowlers(bowler3);
                        System.out.println("Selected Bowler : " + bowler3.getPlayer().getPlayerName());
                        break;
                    }
                }
                if (check == 0) {
                    Bowler s_bowler2 = new Bowler(bowler2);
                    setScoreBoardBowlers(s_bowler2);
                    System.out.println("Selected Bowler : " + bowler2.getPlayerName());
                }

            }

            if (i == (getOverLimit() - 1) || t2 == 1) {
                updateBatsmenListTeam1(currentPlayer.getStriker());
                updateBatsmenListTeam1(currentPlayer.getNonStriker());
            }
            if (getReamaingRun() <= 0 & getID() == 2) {
                break;
            }
        }
        summry(teams[0], teams[1]);

    }

    public void summry(Team team1, Team team2) throws Exception {

        System.out.println("-----------------------------------------------------------------------");
        System.out.println(team1.getName() + " innings                                     " + getTotalRun() + "-"
                + getFallenWickets() + "(" + getTotalOver() + " Ov)");

        System.out.println("------------------------------------------------------------------------");

        System.out.printf("%-10s %-20s %-5s %-5s %-5s %-5s %-5s\n", "Batsman", " ", "R", "B", "4s", "6s", "SR");
        for (int i = 0; i < batsmenListTeam1.size(); i++) {
            Batsman b = batsmenListTeam1.get(i);
            System.out.printf("%-10s %-20s %-5s %-5s %-5s %-5s %-5s\n", b.getPlayer().getPlayerName(), b.outInfo(),
                    b.getRun(), b.getBallPlayed(), b.getFour(), b.getSix(), b.getStrikeRate());
        }
        System.out.println("------------------------------------------------------------------------");

        System.out.println(
                "Extras                                              " + getExtraRun() + "(w " + wideBall + ",nb "
                        + noBall + ")");
        System.out.println(
                "Total                                              " + getTotalRun() + "(" + getFallenWickets()
                        + " wkts," + getTotalOver()
                        + " Ov)");
        System.out.println("------------------------------------------------------------------------");
        System.out.print("Yet to bat : ");
        yetToBat();
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n", "Bowler", " ", "O",
                "M", "R", "W", "NB", "WD", "ECO");

        for (int i = 0; i < bowlerListTeam2.size(); i++) {
            Bowler b = bowlerListTeam2.get(i);
            System.out.printf("%-10s %-15s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n", b.getPlayer().getPlayerName(), " ",
                    b.getOver(), b.getMaidenOver(), b.getRun(), b.getWickets(), b.getNoBall(), b.getWideBall(),
                    b.getEconomy());
        }
        System.out.println("------------------------------------------------------------------------");

    }

    public void yetToBat() {
        for (int i = 0; i < team1Player.size(); i++) {
            Player player = team1Player.get(i);
            System.out.print(player.getPlayerName() + " ");
        }
        System.out.println(" ");
    }

    public void scoreWriter(Team team) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\FinalCricScorer\\ScoreBoard.txt"));
        bw.write(" ");
        bw.newLine();
        bw.write("(" + team.getName() + ")  " + getTotalRun() + " / " + getFallenWickets() + " || " + getTotalOver()
                + " || CRR : " + getCRR());
        bw.newLine();
        bw.write(getCurrentPlayer().getStriker().getPlayer().getPlayerName() + "  "
                + getCurrentPlayer().getStriker().getRun()
                + "(" + getCurrentPlayer().getStriker().getBallPlayed() + ")"
                + " || " + getCurrentPlayer().getNonStriker().getPlayer().getPlayerName() + "  " +
                getCurrentPlayer().getNonStriker().getRun() + "(" + getCurrentPlayer().getNonStriker().getBallPlayed()
                + ")"
                + " || " + getCurrentPlayer().getCurrentBowlers().getPlayer().getPlayerName()
                + " : " + getCurrentPlayer().getCurrentBowlers().getOver() + "("
                + getCurrentPlayer().getCurrentBowlers().getRun() + "/"
                + getCurrentPlayer().getCurrentBowlers().getWickets()
                + ")");
        bw.newLine();
        bw.write(" ");
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public void scoreRead() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("D:\\FinalCricScorer\\ScoreBoard.txt"));
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
    }

    public Player getPlayer(ArrayList<Player> pn) {
        boolean player_check = true;
        String p_name;
        int j;
        for (int i = 0; i < pn.size(); i++) {
            Player player = pn.get(i);
            System.out.print(player.getPlayerName() + " ");
        }
        System.out.println("================");
        do {
            j = 0;
            System.out.print("Enter : ");
            p_name = sc.nextLine();
            while (j < pn.size()) {
                Player player = pn.get(j);
                if (p_name.equalsIgnoreCase(player.getPlayerName())) {
                    player_check = false;
                    break;
                }
                j++;
            }
        } while (player_check);
        return pn.get(j);
    }

    public int getOverLimit() {
        return overLimit;
    }

    public int getID() {
        return ID;
    }

    public int getTotalRun() {
        return totalRun;
    }

    public int getTargetRun() {
        return this.totalRun + 1;
    }

    public int getExtraRun() {
        return extraRun;
    }

    public int getFallenWickets() {
        return fallenWickets;
    }

    public int getTotalBall() {
        return totalBall;
    }

    public Double getCRR() {
        double crr = 0.0;
        try {
            crr = this.totalRun / (this.totalBall / 6.0);
        } catch (NumberFormatException ignored) {

        }
        return crr;
    }

    public String getTotalOver() {

        if (totalBall == 1)
            return "0.1";
        else if (totalBall == 2)
            return "0.2";
        else if (totalBall == 3)
            return "0.3";
        else if (totalBall == 4)
            return "0.4";
        else if (totalBall == 5)
            return "0.5";

        else {
            int over = totalBall / 6;
            int remBall = totalBall % 6;

            return over + "." + remBall;
        }

    }

    public Team[] getTeams() {
        return teams;
    }

    public CurrentPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Batsman> getBatsmenListTeam1() {
        return batsmenListTeam1;
    }

    public ArrayList<Bowler> getBowlersListTeam2() {
        return bowlerListTeam2;
    }
}
