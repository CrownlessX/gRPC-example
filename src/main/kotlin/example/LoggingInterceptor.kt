package example

import io.grpc.*

class LoggingInterceptor : ServerInterceptor, ClientInterceptor {

  // Server interceptor
  override fun <ReqT, RespT> interceptCall(
    call: ServerCall<ReqT, RespT>,
    headers: Metadata,
    next: ServerCallHandler<ReqT, RespT>
  ): ServerCall.Listener<ReqT> {
    println("Server received call: ${call.methodDescriptor.fullMethodName}, Metadata: $headers")
    return next.startCall(call, headers)
  }

  // Client interceptor
  override fun <ReqT, RespT> interceptCall(
    method: MethodDescriptor<ReqT, RespT>?,
    callOptions: CallOptions?,
    next: Channel?
  ): ClientCall<ReqT, RespT> {
    println("Client sending call: ${method?.fullMethodName}, CallOptions: $callOptions")
    return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
      next?.newCall(method, callOptions)
    ) {
      override fun start(responseListener: Listener<RespT>, headers: Metadata) {
        println("Client sending headers: $headers")
        super.start(responseListener, headers)
      }
    }
  }
}
