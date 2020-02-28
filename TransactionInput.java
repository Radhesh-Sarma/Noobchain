public class TransactionInput {
    public String transactionOutputId;
    public TransactionOutput UTXO;//
    //UTXO-Contains Unspent transaction output
    //UTXO is the bitcoin based transaction model

    public TransactionInput(String transactionOutputId) 
    {
        this.transactionOutputId = transactionOutputId;
    }
}
