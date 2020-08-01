# 1.1.2
- Updated jOOQ version to 3.13.4

# 1.1.1
- Updated jOOQ version to 3.13.2

# 1.1.0
- Updated jOOQ version to 3.13.1

# 1.0.0
**Breaking update!**

- Upgraded to jOOQ 3.12.3, which has its own `JSON` and `JSONB` types, which means:
  - Removed `Json` and `Jsonb` classes, replacing them with `JSON` and `JSONB` respectively in `JsonDSL` and `JsonbDSL`
  - Removed `JsonBinding`, `JsonbBinding`, and `JsonStringBinding` as they are now obsolete

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
