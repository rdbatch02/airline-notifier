package config

class ConfigurationException : Exception {
    constructor(ssmPath: String, environmentName: String): super("Unable to populate configuration from SSM at path: $ssmPath or Environment Variable: $environmentName")
}