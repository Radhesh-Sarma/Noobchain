import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet 
{
    public PrivateKey privateKey;//Private key is used to sign the transactions.Users will have to keep their private key secret.
    public PublicKey publicKey;//The public key will act as a address.We can share public key with others to receive payment.
    public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();

    public Wallet()
    {
        generateKeyPair();
    }

    public void generateKeyPair()
    {
        //We will generate our private and public keys in a KeyPair.We will use Elliptic curve Cryptography to generate our key pairs using Java.security.KeyPairGenerator.
        try
        {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDSA","BC");// Elliptic Curve Digital Signature Algorithm 
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keygen.initialize(ecSpec,random);
            KeyPair keyPair = keygen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    public float getBalance()
     {
        //Wallet balance is the sum of all the unspent transaction outputs addressed to you.
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: noobchain.UTXOs.entrySet())
        {
            TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey))//if output belongs to me then those coins belongs to me
            {
                UTXOs.put(UTXO.id,UTXO);//add it to the list of unspent transactions 
                total += UTXO.value ;
            }
        }
        return total;
    }



    public Transaction sendFunds(PublicKey _recipient,float value )
     {
        if(getBalance() < value)
         {
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet())
        {
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
        newTransaction.generateSignature(privateKey);

        for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }


}
