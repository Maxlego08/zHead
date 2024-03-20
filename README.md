# zHead Database

Plugin using minecraft-head.com database

# API

Jitpack: https://jitpack.io/#Maxlego08/zHead

Maven
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.Maxlego08</groupId>
    <artifactId>zHead</artifactId>
    <version>1.1</version>
    <scope>provided</scope>
</dependency>
```

```java

public void example(){
    HeadManager headManager = getProvider(HeadManager.class);
    ItemStack itemStack = headManager.createItemStack(<head id>); # Create an itemstack using an head ID, if the id doesn't exist the itemstack will be null
    List<Head> heads = headManager.search(<value>); # Search for a list of heads based on its name, id, or tag
    Optional<Head> optional = headManager.getHead(<head id>); 
}

public <T> T getProvider(Class<T> classz) {
    RegisteredServiceProvider<T> provider = getServer().getServicesManager().getRegistration(classz);
    return provider == null ? null : provider.getProvider();
}
```
