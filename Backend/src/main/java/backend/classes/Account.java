
package backend.classes;

import java.util.List;

public class Account {
    private int accountNumber;
    private List<Balance> balances;
    private List<Movement> movements;
    
    public Account(){};

    public Account(int accountNumber, List<Balance> balances, List<Movement> movements) {
        this.accountNumber = accountNumber;
        this.balances = balances;
        this.movements = movements;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public List<Movement> getMovements() {
        return movements;
    }
    
    public void addMovement(Movement movement){
        this.movements.add(movement);
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }
    
    
}
