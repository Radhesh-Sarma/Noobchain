import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction 
{
    public String transactionId;
    public PublicKey sender;//address of sender of funds
    public PublicKey reciepient;//address of receiver of funds
    public float value;//amount of funds to be transferred
    public byte[] signature;//this proves the owner of the address is the one sending this transaction and data hasn't been changed

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();//references to previous transactions that prove the sender has funds to send
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();//shows the amount a relevant address received in the transaction.Outputs are inputs in new transactions

    private static int sequence = 0;
    public Transaction(PublicKey from,PublicKey to,float value,ArrayList<TransactionInput> inputs)
    {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash()
    {
        //this will calculate transaction hash which will be its id.
        sequence++;
        return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)+Float.toString(value)+sequence);
    }

    public void generateSignature(PrivateKey privateKey)
    {
        //the private key is used to sign the data to create the signature.
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

    public boolean verifySignature()
    {
        //the public key of the sender is used to verify the signature.
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value);
        return StringUtil.verifyECDSASig(sender,data,signature);

    }


    public boolean processTransaction()
    {

        if(verifySignature() == false )
         {  
         //checks if Signature is valid using public key of the sender.
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        for(TransactionInput i : inputs)
         {
            i.UTXO = noobchain.UTXOs.get(i.transactionOutputId);

        }



        if(getInputsValue() < noobchain.minimumTransaction)
         {
            System.out.println("#Transaction Inputs to small: " + getInputsValue());
            return false;
        }

        float leftOver = getInputsValue() - value;
        transactionId = calculateHash();

        
        //The leftover value is added as a change to the sender.
        outputs.add(new TransactionOutput( this.reciepient, value,transactionId));//Send the value to recipient
        outputs.add(new TransactionOutput( this.sender, leftOver,transactionId));//Send the left over change back to sender

        for(TransactionOutput o : outputs)
         {
            noobchain.UTXOs.put(o.id , o);//add outputs to Unspent list
        }

        //remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs)
         {
            if(i.UTXO == null)
            {
                continue;//if Transaction can't be found skip it
            }
            noobchain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }


    //return the sum of inputs)(UTXOs)
    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) 
        {
            if(i.UTXO == null)
            {
                continue;
            } 
            total += i.UTXO.value;
        }
        return total;
    }

        //return the sum of outputs
    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

}
