package hu.endox.demo.model;

public class Survey implements Model{

    private Long id;
    private String name;
    private Long expectedComplete;
    private Long completionPoint;
    private Long filteredPoint;

    public Survey() {
    }

    public Survey(Long id, String name, Long expectedComplete, Long completionPoint, Long filteredPoint) {
        super();
        this.id = id;
        this.name = name;
        this.expectedComplete = expectedComplete;
        this.completionPoint = completionPoint;
        this.filteredPoint = filteredPoint;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExpectedComplete() {
        return expectedComplete;
    }

    public void setExpectedComplete(Long expectedComplete) {
        this.expectedComplete = expectedComplete;
    }

    public Long getCompletionPoint() {
        return completionPoint;
    }

    public void setCompletionPoint(Long completionPoint) {
        this.completionPoint = completionPoint;
    }

    public Long getFilteredPoint() {
        return filteredPoint;
    }

    public void setFilteredPoint(Long filteredPoint) {
        this.filteredPoint = filteredPoint;
    }

}
