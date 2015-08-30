create column family events
  with comparator=AsciiType and default_validation_class=UTF8Type and key_validation_class=UUIDType
  and column_metadata=[
    {column_name: ref, validation_class: UUIDType},
    {column_name: created_at, validation_class: LongType},
    {column_name: updated_at, validation_class: LongType},
    {column_name: payload, validation_class: UTF8Type}
  ];