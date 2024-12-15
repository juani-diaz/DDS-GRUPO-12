package persistence.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime locTimestamp) {
    return locTimestamp == null ? null : Timestamp.valueOf(locTimestamp);
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
    return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
  }

}
