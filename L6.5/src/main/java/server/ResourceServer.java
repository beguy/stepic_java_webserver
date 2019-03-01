package server;

import resources.TestResource;

public class ResourceServer implements ResourceServerMBean {
    private TestResource resource;

    public ResourceServer() {
        this.resource = new TestResource();
    }

    public ResourceServer(TestResource resource) {
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public int getAge() {
        return resource.getAge();
    }
}
