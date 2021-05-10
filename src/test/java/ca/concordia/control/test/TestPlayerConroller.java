package ca.concordia.control.test;

/**
 * Tests aggressive player.
 */
public class TestPlayerConroller {

    
//    /**
//     * Represents d_player.
//     */
//    Player d_player;
//   
//    
//   
//    /**
//     * Tests if issue_order and next_order functions work correctly or not.
//     * Order must same with the deploy order.
//     */
//    @Test
//    public void testIssueOrder1(){
//    	d_player = new RandomPlayer("Pouria");
//    	d_player.issue_order();
//        Assert.assertEquals(Order.DEPLOY, d_player.next_order());
//    }
//    
//    /**
//     * Tests if issue_order and next_order functions work correctly or not.
//     * Order must same with the advance order.
//     */
//    @Test
//    public void testIssueOrder2(){
//    	d_player = new RandomPlayer("Pouria");
//    	d_player.issue_order();
//    	d_player.next_order();
//        Assert.assertEquals(Order.ADVANCE, d_player.next_order());
//    }
//    
//    /**
//     * Tests if issue_order and next_order functions work correctly or not.
//     * Order must same with the special order.
//     */
//    @Test
//    public void testIssueOrder3(){
//    	d_player = new RandomPlayer("Pouria");
//    	d_player.issue_order();
//    	d_player.next_order();
//    	d_player.next_order();
//        Assert.assertEquals(Order.SPECIAL, d_player.next_order());
//    }
//    
//    /**
//     * Tests if calculation of the reinforcement on each countries work correctly or not
//     */
//    @Test
//    public void calculationofNumberOfreinforcmentArmies() throws IOException {
//    	GameEngine l_gameEngine = new GameEngine();
//    	Continent l_continent = new Continent("USA", 1);
//    	Country l_country = new Country("Canada", l_continent);
//    	ArrayList<RandomPlayer> l_players = new ArrayList<RandomPlayer>();
//    	RandomPlayer l_randomPlayer = new RandomPlayer("Player1");
//    	l_randomPlayer.setOwnedArmies(5);
//    	l_players.add(l_randomPlayer);
//    	StartUpController l_startUpController = new StartUpController();
//    	HashMap<String, Country> l_map = new HashMap<String, Country>();
//    	l_map.put(l_country.getID(), l_country);
//    	l_randomPlayer.setOwnedCountries(l_map);
//    	l_gameEngine.deploy(3, l_country.getID(), l_randomPlayer);
//    	Assert.assertEquals(2, l_randomPlayer.getOwnedArmies());
//    }
//    
//    /**
//     * Tests if check reinforcement maximum pool 
//     */
//    @Test
//    public void checkmaximiumReinforcementPool() {
//    	GameEngine l_gameEngine = new GameEngine();
//    	Continent l_continent = new Continent("USA", 1);
//    	Country l_country = new Country("Canada", l_continent);
//    	ArrayList<RandomPlayer> l_players = new ArrayList<RandomPlayer>();
//    	RandomPlayer l_randomPlayer = new RandomPlayer("Player1");
//    	l_randomPlayer.setOwnedArmies(5);
//    	l_players.add(l_randomPlayer);
//    	StartUpController l_startUpController = new StartUpController();
//    	HashMap<String, Country> l_map = new HashMap<String, Country>();
//    	l_map.put(l_country.getID(), l_country);
//    	l_randomPlayer.setOwnedCountries(l_map);
//    	l_gameEngine.deploy(6, l_country.getID(), l_randomPlayer);
//    	Assert.assertFalse("Invalid Comment", false);
//    }

}
