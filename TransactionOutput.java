import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient;//new owner of the coins
    public float value;//amount of coins they own
    public String parentTransactionId;//ID of the transaction with which this output was created


    //Constructor 
    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
    }


        // check if the coin belongs to you
    public boolean isMine(PublicKey publicKey)
     {
        return (publicKey == reciepient);
    }
}
