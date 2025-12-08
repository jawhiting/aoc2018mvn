# TFL Train Checker

A command-line Kotlin application to check next trains at Deptford Bridge DLR station using the TFL Unified API.

## Setup

1. **Get a TFL API Key** (optional but recommended to avoid rate limiting):
   - Visit: https://api-portal.tfl.gov.uk/
   - Register for a free account
   - Create an application to get your API key

2. **Set the API Key** (optional):
   ```bash
   export TFL_API_KEY=your_api_key_here
   ```

3. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

## Running the Application

### Using Maven:
```bash
mvn compile exec:java -Dexec.mainClass="com.drinkscabinet.tfl.TflTrainCheckerKt"
```

### Or compile and run directly:
```bash
mvn compile
kotlin -cp target/classes:~/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.18.1/jackson-databind-2.18.1.jar:~/.m2/repository/com/fasterxml/jackson/module/jackson-module-kotlin/2.18.1/jackson-module-kotlin-2.18.1.jar com.drinkscabinet.tfl.TflTrainCheckerKt
```

## Features

- Fetches real-time arrival information for Deptford Bridge DLR station
- Displays train destination, platform, and arrival time
- Sorts trains by expected arrival time
- Works with or without API key (rate-limited without key)

## Example Output

```
Fetching next trains at Deptford Bridge DLR station...
============================================================

Next 5 trains:
------------------------------------------------------------
→ Lewisham
  Platform: 2
  Towards: Lewisham
  Arriving in: 2 min (120s)

→ Bank
  Platform: 1
  Towards: Bank
  Arriving in: 5 min (300s)
```

## Station Information

- **Station Name**: Deptford Bridge DLR
- **Station Code**: 940GZZDLDEP
- **Line**: DLR (Docklands Light Railway)

## API Documentation

TFL Unified API Documentation: https://api.tfl.gov.uk/
