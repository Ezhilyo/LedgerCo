package models;

public class LoanDetail {
    private int id;
    private int userId;
    private float sanctionedAmount;
    private int loanTenure;
    private float interestRate;
    private int lastEmiPaidMonth;
    private String name;
    private String bankName;
    private boolean isClosed;
    private float totalRepaymentAmount;
    private float emiAmount;

    public float getAmountPaidSoFar() {
        return amountPaidSoFar;
    }

    public void setAmountPaidSoFar(float amountPaidSoFar) {
        this.amountPaidSoFar = amountPaidSoFar;
    }

    private float amountPaidSoFar;


    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LoanDetail(int id, int user_id, float sanctionedAmount, int loanTenure,
                      float interestRate, String name, String bankName, float totalRepaymentAmount, int emiAmount) {
        this.id = id;
        this.userId = user_id;
        this.sanctionedAmount = sanctionedAmount;
        this.loanTenure = loanTenure;
        this.interestRate = interestRate;
        this.name = name;
        this.bankName = bankName;
        this.lastEmiPaidMonth = 0;
        this.totalRepaymentAmount = totalRepaymentAmount;
        this.emiAmount = emiAmount;
        this.isClosed=false;
    }

    public int getId() {
        return id;
    }


    public float getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(int sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }


    public int getLastEmiPaidMonth() {
        return lastEmiPaidMonth;
    }

    public void setLastEmiPaidMonth(int lastEmiPaidMonth) {
        this.lastEmiPaidMonth = lastEmiPaidMonth;
    }

    public float getTotalRepaymentAmount() {
        return totalRepaymentAmount;
    }

    public float getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(float emiAmount) {
        this.emiAmount = emiAmount;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
