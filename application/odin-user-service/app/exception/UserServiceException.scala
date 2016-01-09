package exception

/**
  * Created by Sa2 on 2015/12/07.
  */
class UserServiceException(message :String = null, cause :Throwable = null) extends RuntimeException(message, cause)
