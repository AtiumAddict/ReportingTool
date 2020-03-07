package uk.co.exus.reportingtool.exception;

public class ResourceNotFoundException extends RuntimeException {

    private Object id;
    private Class clazz;

    public ResourceNotFoundException(Object id) {
        this.id = id;
    }

    public ResourceNotFoundException(Class clazz, Object id) {
        this.clazz = clazz;
        this.id = id;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("Resource with ");
        if (id != null) {
            sb.append(id);
            if (clazz != null) {
                sb.append(" and ");
            }
        }
        if (clazz != null) {
            sb.append("type ").append(clazz.getSimpleName()).append(" ");
        }
        sb.append(" not found ");
        return sb.toString();
    }
}
