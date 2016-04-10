package name.dickinson.duncan.cephalopod.resolver.app

import name.dickinson.duncan.cephalopod.resolver.Resolver
import org.apache.maven.model.Model
import org.eclipse.aether.artifact.Artifact
import org.eclipse.aether.repository.LocalRepository

/**
 *
 *
 * @author Duncan Dickinson
 */
class Main {

    static final scopes = [ compile:
                                    [ [ groupId   : 'org.apache.tika',
                                        artifactId: 'tika-parsers',
                                        version   : '1.10' ] ]
    ]


    static main(args) {
        Resolver resolver = new Resolver(null, new LocalRepository('build/local-repo'))

        def nodes = [ ]

        scopes.each { scope ->
            println "Scope: $scope.key"
            scope.value.each { artifact ->
                println "  - $artifact"
                Artifact a = resolver.getArtifact(artifact.groupId, artifact.artifactId, artifact.version)

                nodes << resolver.getDependencyNode(a, scope.key)

                Model effectiveModel = resolver.getEffectiveModel(a)
                println "$effectiveModel.name version $effectiveModel.version"
                println "  Available versions: ${resolver.getVersions(a)}"

            }
        }
    }
}
