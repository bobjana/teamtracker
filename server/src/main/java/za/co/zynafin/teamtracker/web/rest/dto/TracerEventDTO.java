package za.co.zynafin.teamtracker.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import za.co.zynafin.teamtracker.domain.util.CustomDateTimeDeserializer;
import za.co.zynafin.teamtracker.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TracerEvent entity.
 */
public class TracerEventDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime date;

    @NotNull
    private Long customerId;

    private Long representativeId;

    @NotNull
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(Long representativeId) {
        this.representativeId = representativeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TracerEventDTO tracerEventDTO = (TracerEventDTO) o;

        if ( ! Objects.equals(id, tracerEventDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TracerEventDTO{" +
                "id=" + id +
                ", date='" + date + "'" +
                ", customerId='" + customerId + "'" +
                ", representativeId='" + representativeId + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
