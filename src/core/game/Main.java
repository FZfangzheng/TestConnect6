package core.game;

import Mychoise.My;

public class Main {

    public static void main(String[] args) {

    	GameEvent oucChampion = new GameEvent();
    	//oucChampion.addPlayer(new core.player.Delegate());
    	//oucChampion.addPlayer(new core.player.Dicer());
    	//oucChampion.addPlayer(new baseline.player.AI());
		oucChampion.addPlayer(new FZ16020031016_2.AI5());
		oucChampion.addPlayer(new FZ16020031016.AI5());

		//oucChampion.addPlayer(new My());
    	//oucChampion.addPlayer(new g02.player.AI());
    	//oucChampion.addPlayer(new core.player.Delegate());
    	//oucChampion.addPlayer(new core.player.Dicer());
    	
    	//oucChampion.addPlayer(new g02.player.Lucker());
    	
    	/**
    	 * addPlayer��һ���̣�����Ǵ������ļ������Player����·����
    	 * Ȼ���÷�����ƣ����ɸ�Player�Ķ���
    	 * class.forname("")
    	 */
    	
    	oucChampion.arrangeMatches();
    	
    	oucChampion.run();
    }
}
