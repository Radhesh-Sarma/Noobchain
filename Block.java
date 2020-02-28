import java.util.ArrayList;
import java.util.Date;

public class Block {

	public String hash;
	public String previousHash;
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	public long timeStamp; //as number of milliseconds since 1/1/1970.
	public int nonce;// nonce means number used once .It is an arbitary number which is used once in cryptographic communication.


	//Block Constructor.
	public Block(String previousHash)
	{
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}

	//Calculate new hash based on blocks contents
	public String calculateHash()
	 {
		String calculatedhash = StringUtil.applySha256(previousHash+Long.toString(timeStamp)+Integer.toString(nonce)+merkleRoot);
		return calculatedhash;
	 }

	//Increases nonce value until hash target is reached.
	//mining is a mechanism to add a block to the chain
	public void mineBlock(int difficulty)
	{
		//Mining is a comptational task to find the proof of work.
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		//The Merkle root is the hash of all the hashes of all the transactions in the block
		//Merkle root allows to verify transactions and not include the body of every transaction in the block header while still providing a way to verify the entire blockchain on every transaction
		//If any one trasaction is added/removed or changed it will change the hash of its parent and the merkle root as well
		//So a merkle root is a single value which proves the integrity of all the transactions under it.


		String target = StringUtil.getDifficultyString(difficulty); //Create a string with difficulty * "0"
		
			//here,we calculate the hash while varying the nonce till we get a hash with target number of difficulty in the beginning

		while(!hash.substring( 0, difficulty).equals(target)) 
		{
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	//Add transactions to this block
	public boolean addTransaction(Transaction transaction)
	{
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) 
			{
				return false;
			}
			
		if((previousHash != "0")) 
		{
			if((transaction.processTransaction() != true))
			{
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

}