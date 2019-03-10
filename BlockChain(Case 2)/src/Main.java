public class Main {

  public static void main(String[] args) {
    Blockchain blockchain = new Blockchain(2);
    blockchain.addBlock(blockchain.newBlock("BitCoin1"));
    blockchain.addBlock(blockchain.newBlock("BitCoin2"));
    blockchain.addBlock(blockchain.newBlock("BitCoin3"));
  
    //blockchain.addBlock(new Block(15, System.currentTimeMillis(), "NOOOOOOOOOOO", "BlockInvalid"));
    
    System.out.println("Blockchain valid ? " + blockchain.isBlockChainValid());
    System.out.println(blockchain);
  }

}