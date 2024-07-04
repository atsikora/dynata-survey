package hu.endox.demo.controller;

public class EntityNotFoundException extends RuntimeException {

    private final String entityType;
    private final Long entityId;

    public EntityNotFoundException(String entityType, Long entityId) {
        super();
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

}
