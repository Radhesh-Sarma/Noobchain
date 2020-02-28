import java.security.Security;
import java.util.Random; 
import java.util.ArrayList;
import java.util.HashMap;
import org.bouncycastle.*;
import java.util.*;
import java.util.Scanner;
import java.io.*;
public class noobchain
{

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();//list of all unspent transactions

    public static int difficulty = 2;//It is the required number of zeros at the start of the string.
    public static float minimumTransaction = 0f;
    public static Wallet walletA;
    public static Wallet walletB;
    public static Transaction genesisTransaction;
    public static int p=11;
    public static int g=2;
    
    public static void main(String[] args) 
    {
        //add our blocks to the blockchain ArrayList:
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider



        //Create wallets:
        walletA = new Wallet();
        walletB = new Wallet();
         Wallet org1=new Wallet();
            Wallet org2=new Wallet();
        Wallet coinbase = new Wallet();
      
        //create genesis transaction, which sends 100 NoobCoin to walletA:
        genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 10000f, null);

        genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction
        genesisTransaction.transactionId = "0"; //manually set the transaction id
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.



             System.out.println("Making 2 users with an amount of 5000 in their Wallet\n ");
        //genesis block is the first block of the blockchain.It does not have a previous block so set "0" as the previous hash 
        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);

            Block blockone= new Block(genesis.hash);
            blockone.addTransaction(walletA.sendFunds(walletB.publicKey,5000f));
            addBlock(blockone);


           

                //Menu driven program
        System.out.println("\n Welcome to the Menu");
        System.out.println("There are 2 Organisations:  Organisation 1 and Organisation 2");
        System.out.println("There are 2 Users:   User 1 and User 2");


        Scanner sc=new Scanner(System.in);
            Block[] blockarray= new Block[1000];
            
            int i=0;
            
        int ch,k,a;

        int arr1[]=new int[1000];
        int arr2[]=new int[1000];
        int arr3[]=new int[1000];

  
    
                
        while(true)
     {

           
                System.out.println("Press 1 if you are user 1");
                System.out.println("Press 2 if you are user 2");
                System.out.println("Press 3 to view transaction History");

                ch=sc.nextInt();

                arr1[i]=ch;


                switch(ch)
                {
                    case 1:
                            if(zkpdiscretelog(5,9)==false)
                            {
                                break;
                            }
                        System.out.println("Press 1 to donate to Organisation 1");
                        System.out.println("Press 2 to donate to Organisation 2");
                        System.out.println("Press 3 to show your balance");
                        k=sc.nextInt();
                        arr2[i]=k;
                        if(k==1)
                        {
                             System.out.println("Enter amount to donate");
                            a=sc.nextInt();
                            float ab=(float)(a);
                                    arr3[i]=a;


                             blockarray[i]=new Block(blockchain.get(blockchain.size()-1).hash);
                             blockarray[i].addTransaction(walletA.sendFunds(org1.publicKey,ab));
                                addBlock(blockarray[i]);
                                        i++;
                                 System.out.println("Now Your balance is " + walletA.getBalance());
                             

                        }
                        else if(k==2)
                        {

                                  System.out.println("Enter amount to donate");
                            a=sc.nextInt();
                            float ab=(float)(a);

                                arr3[i]=a;
                             blockarray[i]=new Block(blockchain.get(blockchain.size()-1).hash);
                             blockarray[i].addTransaction(walletA.sendFunds(org2.publicKey,ab));
                                addBlock(blockarray[i]);
                                        i++;
                                 System.out.println("Now Your balance is " + walletA.getBalance());
                             

                        }
                        else if(k==3)
                        {
                            System.out.println("Your balance is " + walletA.getBalance());

                        }
                        else
                        {
                                System.out.println("Wrong Entry.Please try again"); 
                            
                        }

                        break;
                    case 2:
                                
                        if(zkpdiscretelog(5,9)==false)
                            {
                                break;
                            }
                        System.out.println("Press 1 to donate to Organisation 1");
                        System.out.println("Press 2 to donate to Organisation 2");
                        System.out.println("Press 3 to show your balance");
                        k=sc.nextInt();
                            arr2[i]=k;
                        if(k==1)
                        {
                                     System.out.println("Enter amount to donate");
                            a=sc.nextInt();
                            float ab=(float)(a);

                                    arr3[i]=a;

                              blockarray[i]=new Block(blockchain.get(blockchain.size()-1).hash);
                             blockarray[i].addTransaction(walletB.sendFunds(org1.publicKey,ab));
                                addBlock(blockarray[i]);
                                        i++;
                                 System.out.println("Now Your balance is " + walletA.getBalance());
                             
                        }
                        else if(k==2)
                        {
                                   System.out.println("Enter amount to donate");
                            a=sc.nextInt();
                            float ab=(float)(a);
                                        arr3[i]=a;

                              blockarray[i]=new Block(blockchain.get(blockchain.size()-1).hash);
                             blockarray[i].addTransaction(walletB.sendFunds(org2.publicKey,ab));
                                addBlock(blockarray[i]);
                                        i++;
                                 System.out.println("Now Your balance is " + walletA.getBalance());
                        }
                        else if(k==3)
                        {
                            System.out.println("Your balance is " + walletB.getBalance());

                        }
                        else
                        {
                                System.out.println("Wrong Entry.Please try again"); 
                        } 
                            break;
                    case 3:
                        	
                        	{
                                System.out.println("\nUser1's  balance is " + walletA.getBalance());
                    System.out.println("User2's  balance is " + walletB.getBalance());
                    System.out.println("Organization 1's  balance is " + org1.getBalance());
                    System.out.println("Organization 2's  balance is " + org2.getBalance());
                    



                    while(true)
                    {
                        System.out.println("Press 1 to view user 1 Transaction History");
                        System.out.println("Press 2 to view user 2 Transaction History");
                        System.out.println("Press 3 to exit ");
                        k=sc.nextInt();
                        if(k==1)
                        {   
                            boolean ok=false;

                                for(int z=0;z<1000;z++)
                                {
                                    if(arr2[z]==1||arr2[z]==2)
                                    {
                                        if(arr1[z]==1)
                                        {   
                                            ok=true;
                                            System.out.println("User 1 donated "+ arr3[z] + " to Organisation "+arr2[z]);
                                        }
                                    }
                                }
                                if(ok==false)
                                {
                                    System.out.println("No transaction done by User 1");
                                }
                        }
                        else if(k==2)
                        {
                            boolean ok=false;
                                  for(int j=0;j<1000;j++)
                                {
                                    if(arr2[j]==1||arr2[j]==2)
                                    {
                                        if(arr1[j]==2)
                                        {
                                            ok=true;
                                            System.out.println("User 2 donated "+ arr3[j] + " to Organisation "+arr2[j]);
                                        }
                                    }
                                }
                                 if(ok==false)
                                {
                                    System.out.println("No transaction done by User 1");
                                }

                        }
                        else if(k==3)
                        {
                            System.exit(0);
                        }
                        else
                        {
                            System.out.println("Wrong Entry.Please try again"); 
                        }

                    }    





                            }
                        	

                    default: System.out.println("Wrong Entry.Please try again");  
                }


                    

                    
        }



        }

     




    

    public static Boolean isChainValid()
    {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++)
        {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) )
            {
                System.out.println("#Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) )
            {
                System.out.println("#Previous Hashes not equal");
                return false;
            }

            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget))
            {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            //loop thru blockchains transactions:
            TransactionOutput tempOutput;
            for(int t=0; t <currentBlock.transactions.size(); t++)
            {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if(!currentTransaction.verifySignature())
                {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue())
                {
                    System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
                    return false;
                }

                for(TransactionInput input: currentTransaction.inputs)
                {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);

                    if(tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }

                    if(input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }

                    tempUTXOs.remove(input.transactionOutputId);
                }

                for(TransactionOutput output: currentTransaction.outputs)
                {
                    tempUTXOs.put(output.id, output);
                }

                if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient)
                {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender)
                {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }

    public static void addBlock(Block newBlock)
    {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
    
    public static int expo(int a,int b,int c)
    {
    	int ans=1;
    	for(int i=0;i<b;i++)
    	{
    		ans=((ans%c)*(a%c))%c;
    	}
    	return ans;
    	
    }
    
    public static Boolean ZKP(int x)
    {
    	Random rand = new Random();
    	
    	int y=expo(g,x,p);
    	int r=rand.nextInt(p-1);
    	int h=expo(g,r,p);
    	int b=rand.nextInt(2);
    	int s=(r+b*x)%(p-1);
    	int val1=expo(g,s,p),val2=(h*expo(y,b,p))%p;
    	if(val1==val2)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    public static Boolean zkpdiscretelog(int y1,int y2)
    {

        Random rand = new Random();
         Scanner sc=new Scanner(System.in);
         System.out.println("\nKindly verify yourself as a user");
         System.out.println("Zero Knowledge Proof");
        System.out.println("Choose a random number between 0 and 9");
        System.out.println("Please compute h=(2^r)(mod 11) and Enter h");
        int h=sc.nextInt();
        System.out.println("h is "+ h );
        int b=rand.nextInt(2);
        System.out.println("Random bit is"+b);
        System.out.println("Please compute s=(r+b*x)mod(10).Here x is the number you are proving you know");
        int s=sc.nextInt();

        int val1=expo(2,s,11);
        int val2=(h*expo(y1,b,11))%11;
        int val3=(h*expo(y2,b,11))%11;

        if(val1==val2)
        {
             System.out.println("Zero Knowledge Proof Successful.You are verified as User 1");
             return true;
        }
        else if(val1==val3)
        {
            System.out.println("Zero Knowledge Proof Successful.You are verified as User 1");
            return true;
        }
        else
        {
            System.out.println("Zero Knowledge Proof Failed.Please try again");
            return false;
        }
    }
    
}