This is a REST service with functionality to add and read an account. A new feature is added to it i.e. transferring of money between accounts. The technologies used for this projects are  Java 8, Gradle, Springboot, Lombok, Junit 4, AssertJ Framework. 

The components of the feature "transferring of money between accounts" are :
1)TransferMoneyDetails : It is a model which holds fromAccountNumber(Sender's Account), toAccountNumber(receiver's account), amount(transaction amount).
2)TransactionInfo : It is an entity(mapped with Table = "TransactionInfo") which holds fields transactionId, fromAccountNumber, toAccountNumber, transactionAmount, transactionDateTime.
3)TransactionRepository : It is a repository class which extends JPA Repository. It is used to save transaction details in TransactionInfo table.
4)AccountsController : This component is used to delegate request from the end user for transferring of money between accounts to the service class.
It will show Http status as OK(200) if the transaction is properly made. In the case of insufficient balance in the sender's account, it will show Http status as Bad Request(400)
5)AccountsService : This component will implement the business logic for transferring of money between accounts.EmailNotificationService sends notification mails both accounts if the transaction committed.
6)InsufficientBalanceException : If the transactionAmount > Sender's account balance, this class will handle the exception.

Steps to Run the application:
1)Import the project as existing gradle project in STS/Eclipse.
2)Configure workspaceSetting with Local installation directory with proper gradle binary from local system.
3)Select the project-> Gradle-> Refresh Gradle Project
4)Select the Project -> Run As Springboot App OR go to the main class "DevChallengeApplication.java" -> Run As Springboot App/Java Application
5)Application will start on default port 8080 or the server port mentioned in application.yml file.
6)Hit the request in Postman with proper http method, url and payload and verify the result.
7)Select the project -> Run As > JUnit Test (Eclipse/STS)

Further Improvement :
1)Use of JPA Repository instead of inMemory repository.
2)Use error handling classes for errorneous scenerios.
3)Save the transaction details with transaction date time in a particular table.
4)Proper Frontend UI will be provided to test manually.
5)More custom exception scenerios need to be handled.









 
