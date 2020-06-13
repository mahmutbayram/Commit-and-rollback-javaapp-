# Commit-and-rollback-javaapp


    
    Most of the time, we do many database operations in our programs one after another.
    For example, we have 3 linked table updates (delete, update).
    Let's say we run these processes and queries consecutively.
    
    statement.executeupdat A (query1);
    statement.executeupdat A (sorgu2); // There was an error here and our program is over.
    statement.executeupdat A (sorgu3);
    
    
    In such a case, there is an error in our 2nd query.
    However, since there was no error in query 1, this query updated our database.
    However, if these queries are related, query 1 should not work.
    Here we use Transactions to prevent such situations.
    
    (ATM Example)
    
    To use transaction logic, these queries are only
    we want it to work collectively.
    
    From the moment we write these queries, Java automatically queries without queries
    It runs. So, by doing something like con.setAutoCommit (false) first
    We're blocking.
    
    commit (): Run all queries. We use it to run all of them when there is no problem.
    rollback (): Cancel all queries. We use it to not run any of them when there is a problem.
    
    
    So this time, we have written our programs a little safer.
    
    Note: Even if we write setAutoCommit (false), we have a query that doesn't update the Database,
    it is operated as there will be no security problems.
    
