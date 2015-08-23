create column family users
  with comparator=AsciiType and default_validation_class=UTF8Type and key_validation_class=UUIDType
  and column_metadata=[
    {column_name: ref, validation_class: UUIDType},
    {column_name: name, validation_class: UTF8Type},
    {column_name: email, validation_class: UTF8Type},
    {column_name: password, validation_class: UTF8Type},
    {column_name: created_at, validation_class: LongType},
    {column_name: updated_at, validation_class: LongType},
    {column_name: last_seen, validation_class: LongType}
  ];