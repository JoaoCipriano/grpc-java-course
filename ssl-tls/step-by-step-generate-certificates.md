# Generate Certificates

[Digicert Tutorial](https://www.digicert.com/kb/ssl-support/openssl-quick-reference-guide.htm)

### Become CA:

* create a private key first for CA (des3 is encryption standard)

````shell
openssl genrsa -des3 -out ca.key.pem 2048
````

* create ca certificate (This certificate is used to sign the server certificate and client will use this later)

````shell
openssl req -x509 -new -nodes -key ca.key.pem -sha256 -days 365 -out ca.cert.pem
````

### Server:

* create a private key for your server

````shell
openssl genrsa -out localhost.key 2048
````

* As service owner, you create a request for sending to CA

````shell
openssl req -new -key localhost.key -out localhost.csr
````

### CA signs your request

* CA signs your request which you need to keep it safe

````shell
openssl x509 -req -in localhost.csr -CA ca.cert.pem -CAkey ca.key.pem -CAcreateserial -out localhost.crt -days 365
````

### gRPC specific step:

* grpc expects the localhost key in PKCS8 standard

````shell
openssl pkcs8 -topk8 -nocrypt -in localhost.key -out localhost.pem
````
