package name.dickinson.duncan.resolver.old

String mvnMetaFile = 'maven-metadata.xml'

String repoBase = 'http://jcenter.bintray.com'
String dependency = 'org.apache.tika:tika-parsers:1.10'

Expando pomInfo = new Expando()

dependency.tokenize(':').with {
    pomInfo.group = it[0]
    pomInfo.artifact = it[1]
    pomInfo.version = it[2]
}

pomInfo.downloadPath = "${pomInfo.group.replace('.', '/')}/${pomInfo.artifact}"
pomInfo.downloadBase = "$repoBase/$pomInfo.downloadPath"

mvnMeta = new XmlSlurper().parseText("$pomInfo.downloadBase/$mvnMetaFile".toURL().text)
pomInfo.versions = mvnMeta.versioning.versions.version.collect { it.text() }
pomInfo.latestVersion = mvnMeta.versioning.latest
pomInfo.lastUpdated = mvnMeta.versioning.lastUpdated

pomInfo.pomUrl = "$pomInfo.downloadBase/$pomInfo.version/$pomInfo.artifact-${pomInfo.version}.pom"

def pom = new XmlSlurper().parseText(pomInfo.pomUrl.toURL().text)

pomInfo.name = pom.name
pomInfo.url = pom.url
pomInfo.organization = [
        name: pom.organization.name,
        url : pom.organization.url]

pomInfo.parent = [
        group   : pom.parent.groupId,
        artifact: pom.parent.artifactId,
        version : pom.parent.version
]

pomInfo.scm = [:]
pomInfo.scm.url = pom.scm.url

pomInfo.issueManagement = [:]
pomInfo.issueManagement.system = pom.issueManagement.system
pomInfo.issueManagement.url = pom.issueManagement.url

def issues = new groovy.json.JsonSlurper().parseText('https://issues.apache.org/jira/rest/api/2/project/TIKA'.toURL().text)

pomInfo.issueManagement.api = issues.self
pomInfo.issueManagement.title = issues.description
pomInfo.issueManagement.avatarUrl = issues.avatarUrls."48x48"
pomInfo.issueManagement.components = issues.components.collect {
    [name: it.name,
     uri : it.self]
}

pomInfo.issueManagement.issueTypes = issues.issueTypes.collect {
    [name       : it.name,
     description: it.description,
     uri        : it.self]
}

pomInfo.issueManagement.issueTypes = issues.versions.collect {
    [name       : it.name,
     description: it.description,
     released   : it.released,
     releaseDate: it.releaseDate,
     uri        : it.self]
}

pomInfo.ciManagement = [:]
pomInfo.ciManagement.system = pom.ciManagement.system
pomInfo.ciManagement.url = pom.ciManagement.url
pomInfo.ciManagement.api = pom.ciManagement.url + 'api'

//Open HUB: https://www.openhub.net/p/tika

for (prop in pomInfo.properties) {
    println "  - $prop"
}
