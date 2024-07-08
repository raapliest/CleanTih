import java.time.LocalDate;
import java.time.LocalTime;

public class Report {
    private String report;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String location;

    public Report(String report, LocalDate date, LocalTime time, String description, String location) {
        this.report = report;
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
    }

    // Getters and setters
    public String getReport() { 
        return report; 
    
    }
    public void setReport(String report) { 
        this.report = report; 
    }

    public LocalDate getDate() { 
        return date; 
    }
    public void setDate(LocalDate date) { 
        this.date = date; 
    }

    public LocalTime getTime() { 
        return time; 
    }
    public void setTime(LocalTime time) { 
        this.time = time; 
    }

    public String getDescription() { 
        return description; 
    }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public String getLocation() { 
        return location; 
    }
    public void setLocation(String location) { 
        this.location = location; 
    }
}
