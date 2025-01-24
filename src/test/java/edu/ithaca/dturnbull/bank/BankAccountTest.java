package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@abc.com"));   // valid email address
        assertTrue(BankAccount.isEmailValid("abc@abc.edu")); // valid email address with new TLD
        assertTrue(BankAccount.isEmailValid("a.b@abc.com"));
        assertTrue(BankAccount.isEmailValid("abc+tag@abc.com"));   // valid email address with tag
        assertTrue(BankAccount.isEmailValid("a.b+tag@abc.com"));
        assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@example.com")); // valid email address with special characters
        assertTrue(BankAccount.isEmailValid("address\"Why is this Legal?@#@#@#\"@abc.com"));                         // Ususlly not allowed
        assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@[1.0.0.127]"));                                    // Ususlly not allowed
        assertTrue(BankAccount.isEmailValid("!#$%&'*+-/=.?^_`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]")); // Ususlly not allowed
        assertTrue(BankAccount.isEmailValid("a.b(Email is a strange Format)@abc.com"));                              // Ususlly not allowed




        
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
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}