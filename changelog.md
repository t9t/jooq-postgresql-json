# 0.4.0
- Added a handful of processing functions:
    - `json(b)_array_length`
    - `json(b)_extract_path`
    - `json(b)_extract_path_text`
    - `json(b)_typeof`
    - `json(b)_strip_nulls`
    - `jsonb_pretty`

# 0.3.1
- Fix release to Maven central repository

# 0.3.0
- Added `Json` and `Jsonb` to encapsulate `json` and `jsonb` fields and values
- Added `JsonBinding` and `JsonbBinding` to map `json` and `jsonb` fields to the above
- Added `JsonDSL` and `JsonbDSL` containing PostgreSQL `json` and `jsonb` operators

# 0.2.0
- Split `JsonbStringBinding` for `jsonb` to leverage potential performance benefit.

# 0.1.0
- Initial release containing only `JsonStringBinding`.
