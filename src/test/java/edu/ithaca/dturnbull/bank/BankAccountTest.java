package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        //check for correct balance
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        //check for correct withdrawal
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        //test that exceptions are thrown correctly
        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(10.0001));
    }

    @Test
    void isEmailValidTest(){
        //testing valid email addresses
        assertTrue(BankAccount.isEmailValid( "a@abc.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid("abc@abc.edu")); // valid email address with new TLD
        assertTrue(BankAccount.isEmailValid("a.b@abc.com"));
        assertTrue(BankAccount.isEmailValid("abc+tag@abc.com"));   // valid email address with tag
        //assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@example.com")); // valid email address with special characters
        //assertTrue(BankAccount.isEmailValid("address\"Why is this Legal?@#@#@#\"@abc.com"));                         // Ususlly not allowed
        //assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@[1.0.0.127]"));                                    // Ususlly not allowed
        //assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]")); // Ususlly not allowed
        //assertTrue(BankAccount.isEmailValid("a.b(Email is a strange Format)@abc.com"));                              // Ususlly not allowed

        //testing invalid email addresses
        assertFalse( BankAccount.isEmailValid(""));      // empty string
        assertFalse( BankAccount.isEmailValid("abc"));   // no @ sign
        assertFalse( BankAccount.isEmailValid("@n"));    // no character before @
        assertFalse( BankAccount.isEmailValid("a..b@abc.com"));   // two consecutive dots
        assertFalse( BankAccount.isEmailValid(".a@abc.com"));  // starts with a dot
        assertFalse( BankAccount.isEmailValid("a.@abc.com")); // ends with a dot
        assertFalse( BankAccount.isEmailValid("abc\\@abc.com")); // cannot end with a \
        assertFalse( BankAccount.isEmailValid("a@abc.c"));     // TLD with only 1 letter
        assertFalse( BankAccount.isEmailValid("spaces\\ must\\ be\\ within\\ quotes\\ even\\ when\\ escaped@example.com")); // spaces must be within quotes, even when escaped
        assertFalse( BankAccount.isEmailValid("abc@abc@abc.c")); // two @ signs This si only allowed inside quotes
    }

    @Test
    void isEmailValidOwensVersionTest(){
        //Testing valid email addresses
        assertTrue(BankAccount.isEmailValidOwensVersion("a@abc.com"));   // valid email address
        assertTrue(BankAccount.isEmailValidOwensVersion("abc@abc.edu")); // valid email address with new TLD
        assertTrue(BankAccount.isEmailValidOwensVersion("a.b@abc.com"));
        assertTrue(BankAccount.isEmailValidOwensVersion("abc+tag@abc.com"));   // valid email address with tag
        assertTrue(BankAccount.isEmailValidOwensVersion("a.b+tag@abc.com"));
        assertTrue(BankAccount.isEmailValidOwensVersion("a!#$%&'*+-/=.?^_`{|}~s@example.com")); // valid email address with special characters
        assertTrue(BankAccount.isEmailValidOwensVersion("a!#$%&'*+-/=.?^_`{|}~s@[1.0.0.127]"));                                    // Ususlly not allowed
        assertTrue(BankAccount.isEmailValidOwensVersion("a!#$%&'*+-/=.?^_`{|}~s@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]")); // Ususlly not allowed
        //assertTrue(BankAccount.isEmailValidOwensVersion("a.b(Email is a strange Format)s@abc.com"));                                   // Almost never allowed
        //assertTrue(BankAccount.isEmailValidOwensVersion("address\"Why is this Legal?@#@#@#\"@abc.com"));                               // Almost never allowed

        //Testing invalid email addresses
        assertFalse( BankAccount.isEmailValidOwensVersion(""));                 // empty string
        assertFalse( BankAccount.isEmailValidOwensVersion("abc"));              // no @ sign
        assertFalse( BankAccount.isEmailValidOwensVersion("@n"));               // no character before @
        assertFalse( BankAccount.isEmailValidOwensVersion("a..b@abc.com"));     // two consecutive dots
        assertFalse( BankAccount.isEmailValidOwensVersion(".a@abc.com"));       // starts with a dot
        assertFalse( BankAccount.isEmailValidOwensVersion("a.@abc.com"));       // ends with a dot
        assertFalse( BankAccount.isEmailValidOwensVersion("abc\\@abc.com"));    // cannot end with a \
        assertFalse( BankAccount.isEmailValidOwensVersion("a@abc.c"));          // TLD with only 1 letter
        assertFalse( BankAccount.isEmailValidOwensVersion("spaces\\ must\\ be\\ within\\ quotes\\ even\\ when\\ escaped@example.com")); // spaces must be within quotes, even when escaped
        assertFalse( BankAccount.isEmailValidOwensVersion("abc@abc@abc.c"));    // two @ signs This si only allowed inside quotes
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        //check for correct initialization
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exceptions thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@abc.com", -100));   
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abc@abc.com", 100.001));

    }

    @Test
    void isAmountValidTest() {
        //testing valid amounts
        assertTrue(BankAccount.isAmountValid(100));
        assertTrue(BankAccount.isAmountValid(0.1));
        assertTrue(BankAccount.isAmountValid(100.01));
        //testing invalid amounts
        assertFalse(BankAccount.isAmountValid(100.001));
        assertFalse(BankAccount.isAmountValid(-100));
        assertFalse(BankAccount.isAmountValid(0));
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        //check for correct deposit
        bankAccount.deposit(100);
        assertEquals(200, bankAccount.getBalance(), 0.001);
        bankAccount.deposit(0.01);
        assertEquals(200.01, bankAccount.getBalance(), 0.001);
        //check for exceptions thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit( -100));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit( 100.001));  
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit( 0));
    }

    @Test
    void transferTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 1000);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 100);        
        // check for correct transfer
        try{
        bankAccount.transfer(bankAccount2,100);}
        catch(InsufficientFundsException e){
            throw new IllegalArgumentException("Insufficient Funds",e);
        }
        assertEquals(900, bankAccount.getBalance(), 0.001);
        assertEquals(200, bankAccount2.getBalance(), 0.001);
        
        //check correct transfer with decimal
        try{
            bankAccount.transfer(bankAccount2,0.01);}
            catch(InsufficientFundsException e){
                throw new IllegalArgumentException("Insufficient Funds",e);
            }
        assertEquals(899.99, bankAccount.getBalance(), 0.001);
        assertEquals(200.01, bankAccount2.getBalance(), 0.001);
        // check for exceptions thrown correctly
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2,-100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2,100.001));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2,0));
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(bankAccount2,1000));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(null,100));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount,100));
    }
}