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
