package com.shoppingcart.functional


sealed class Result<out ErrorCode,out T>{

    data class Failure<ErrorCode>(val errorCode: ErrorCode) : Result<ErrorCode,Nothing>()
    data class Success<T>(val value : T) : Result<Nothing,T>(){
        companion object{
            operator fun invoke(): Result<Nothing, Unit> = Success(Unit)
        }
    }
    companion object{
        fun <ERR : ErrorCode> failure(errorCode: ErrorCode) : Result<ErrorCode,Nothing> = Failure(errorCode)

        fun <ERR : ErrorCode, T, U> Result<ERR, T>.map(f : (T) -> U ) : Result<ERR,U> {
              return  when(this){
                    is Success -> Success(f(value))
                    is Failure -> this
                }
            }


       fun <ERR : ErrorCode, T> Result<ERR, T>.orElse(f : (ERR) -> T): T {
         return  when(this){
               is Success -> this.value
               is Failure -> f(errorCode)
           }
      }

     fun  <T> T.asSuccess(): Result<Nothing,T> = Success(this)

     fun  <ERR : ErrorCode> ERR.asFailure(): Result<ERR,Nothing> = Failure(this)
    }

}