namespace BabyEye.Utils.States
{
    public interface ICrudResult {
        public bool isSuccess { get; }
    }

    public class Success<T> : ICrudResult
    {
        public T? Data { get; set; }
        public bool isSuccess { get; } = true;

        public Success() { }

        public Success(T data)
        {
            Data = data;
        }
    }

    public class NotFound : Exception, ICrudResult
    {
        public bool isSuccess { get; } = false;

        public NotFound(string? errorMsg = null) : base(errorMsg) { }
    }

    public class ElementAlreadyExists : Exception, ICrudResult
    {
        public bool isSuccess { get; } = false;

        public ElementAlreadyExists(string? errorMsg = null) : base(errorMsg) { }
    }

    public class UnexpectedError : Exception, ICrudResult 
    {
        public bool isSuccess { get; } = false;

        public UnexpectedError(string? errorMsg = null) : base(errorMsg) { }
    }
}
