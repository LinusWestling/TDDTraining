
// Isolate database connections from the rest of the code, both architectural and security advantages. Only verify succesful CRUD operations in business logic.
//      Can operate atomically to handle several payments at once.
//          LoadBalancer can be implemented as a layer to access this operation to handle high traffic.