# Proxynaut 
It's a custom proxy that leaves parameters and request body unchanged, adding the Authorization header
In case of code 200 is returned, sends back the answer without changes
In else, returns code 500

Use _application.properties_ file with your real data

```
--spring.config.location=/path/to/your/config/
```
