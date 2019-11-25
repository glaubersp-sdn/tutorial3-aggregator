# Tutorial 3 ODL
## Compilação de um modelo YANG simples.
*Fonte*: https://zoo.cs.yale.edu/classes/cs434/cs434-2019-spring/assignments/ps1/

1. Editar o arquivo `tutorial3/api/src/main/yang/tutorial3.yang`
2. Editar o arquivo `tutorial3/impl/src/main/java/br/com/padtec/odl/tutoriais/impl/Tutorial3Listener.java`
3. Editar o arquivo `tutorial3/impl/src/main/resources/OSGI-INF/blueprint/impl-blueprint.xml`
4. Alterar o arquivo api/pom.xml para incluir as dependências de modelos (modelos YANG dos quais o modelo acima depende ou que importa)

```
<dependencies>
        <!--odl-mdsal-models-->
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>iana-afn-safi</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.binding.model.iana</groupId>
            <artifactId>iana-if-type</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.binding.model.ietf</groupId>
            <artifactId>rfc6991</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.binding.model.ietf</groupId>
            <artifactId>rfc7223</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-restconf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>opendaylight-l2-types</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-ted</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-topology</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-topology-isis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-topology-ospf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-topology-l3-unicast-igp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-type-util</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-packet-fields</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-access-control-list</artifactId>
        </dependency>
        <dependency>
            <groupId>org.opendaylight.mdsal.model</groupId>
            <artifactId>ietf-lisp-address-types-2015-11-05</artifactId>
        </dependency>
    </dependencies>   
```

5. Compilar:

        mvn clean install

6. Executar o Karaf/ODL:

        ./karaf/target/assembly/bin/karaf clean

7. Ativar o DEBUG para os pacotes do projeto:

        opendaylight-user@root>log:set DEBUG br.com.padtec

8. Executar as chamadas REST no arquivo `odl-tutorial.rest` e verificar a resposta do GET:

Primeiro cadastro:

```
POST http://localhost:8181/restconf/config/tutorial1:access-list/

HTTP/1.1 204 No Content
Set-Cookie: JSESSIONID=node01og1b51key6mk1rsdwx963baw10.node0;Path=/
Expires: Thu, 01 Jan 1970 00:00:00 GMT
Set-Cookie: rememberMe=deleteMe; Path=/; Max-Age=0; Expires=Mon, 18-Nov-2019 16:35:18 GMT
Location: http://localhost:8181/restconf/config/tutorial1:access-list

<Response body is empty>

Response code: 204 (No Content); Time: 1286ms; Content length: 0 bytes

Cookies are preserved between requests:
> /home/gcabral/desenvolvimento/ODL-dev/tutorial1/.idea/httpRequests/http-client.cookies
```

Verificar no log do Karaf:

```
08:53:01.891 INFO [opendaylight-cluster-data-notification-dispatcher-44] Tutorial3 App listener onDataTreeChanged [LazyDataTreeModification{path=DataTreeIdentifier{datastore=CONFIGURATION, root=InstanceIdentifier{targetType=interface org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList, path=[org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList]}}, rootNode=LazyDataObjectModification{identifier=org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList, domData=ChildNode{mod = ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list, operation=MERGE, modificationType=WRITE, childModification={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry=ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry, operation=MERGE, modificationType=SUBTREE_MODIFIED, childModification={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=permit1}]=ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=permit1}], operation=WRITE, modificationType=WRITE}}}}}, oldMeta = null, newMeta = SimpleContainerNode{version=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@f241520, data=ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list, value=[ImmutableMapNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry, value=[ImmutableMapEntryNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=permit1}], value=[ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)actions, value=[ImmutableChoiceNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)packet-handling, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)permit, value=empty}]}]}, ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)matches, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)source-ipv4-network, value=10.0.0.1/32}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)destination-ipv4-network, value=10.0.0.2/32}]}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name, value=permit1}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)node-id, value=openflow:1}]}]}]}}}}}] 
08:53:01.915 INFO [opendaylight-cluster-data-notification-dispatcher-44]  Write after data AccessList{getAccessListEntry=[AccessListEntry{getActions=Actions{getPacketHandling=Permit{getPermit=empty, augmentations={}}, augmentations={}}, getMatches=Matches{getDestinationIpv4Network=Ipv4Prefix{_value=10.0.0.2/32}, getSourceIpv4Network=Ipv4Prefix{_value=10.0.0.1/32}, augmentations={}}, getNodeId=openflow:1, getRuleName=permit1, augmentations={}}], augmentations={}} 
08:53:01.915 INFO [opendaylight-cluster-data-notification-dispatcher-44]  Printing the AccessList Entries : 
08:53:01.916 INFO [opendaylight-cluster-data-notification-dispatcher-44]  Rule Name : permit1
08:53:01.916 INFO [opendaylight-cluster-data-notification-dispatcher-44]  NodeList :  openflow:1
08:53:01.916 INFO [opendaylight-cluster-data-notification-dispatcher-44]  Actions : Actions{getPacketHandling=Permit{getPermit=empty, augmentations={}}, augmentations={}}
08:53:01.916 INFO [opendaylight-cluster-data-notification-dispatcher-44]  Matches Matches{getDestinationIpv4Network=Ipv4Prefix{_value=10.0.0.2/32}, getSourceIpv4Network=Ipv4Prefix{_value=10.0.0.1/32}, augmentations={}}
08:53:01.917 INFO [opendaylight-cluster-data-notification-dispatcher-44]  AccessListEntry : AccessListEntry{getActions=Actions{getPacketHandling=Permit{getPermit=empty, augmentations={}}, augmentations={}}, getMatches=Matches{getDestinationIpv4Network=Ipv4Prefix{_value=10.0.0.2/32}, getSourceIpv4Network=Ipv4Prefix{_value=10.0.0.1/32}, augmentations={}}, getNodeId=openflow:1, getRuleName=permit1, augmentations={}}
```

Segundo cadastro:
```
POST http://localhost:8181/restconf/config/tutorial1:access-list/

HTTP/1.1 204 No Content
Location: http://localhost:8181/restconf/config/tutorial1:access-list

<Response body is empty>

Response code: 204 (No Content); Time: 39ms; Content length: 0 bytes

Cookies are preserved between requests:
> /home/gcabral/desenvolvimento/ODL-dev/tutorial1/.idea/httpRequests/http-client.cookies
```

Verificar no log do Karaf:

```
08:56:56.260 INFO [opendaylight-cluster-data-notification-dispatcher-43] Tutorial3 App listener onDataTreeChanged [LazyDataTreeModification{path=DataTreeIdentifier{datastore=CONFIGURATION, root=InstanceIdentifier{targetType=interface org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList, path=[org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList]}}, rootNode=LazyDataObjectModification{identifier=org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.tutorial3.rev191119.AccessList, domData=ChildNode{mod = ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list, operation=MERGE, modificationType=SUBTREE_MODIFIED, childModification={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry=ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry, operation=MERGE, modificationType=SUBTREE_MODIFIED, childModification={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=deny1}]=ModifiedNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=deny1}], operation=WRITE, modificationType=WRITE}}}}}, oldMeta = SimpleContainerNode{version=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@f241520, data=ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list, value=[ImmutableMapNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry, value=[ImmutableMapEntryNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=permit1}], value=[ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)actions, value=[ImmutableChoiceNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)packet-handling, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)permit, value=empty}]}]}, ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)matches, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)source-ipv4-network, value=10.0.0.1/32}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)destination-ipv4-network, value=10.0.0.2/32}]}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name, value=permit1}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)node-id, value=openflow:1}]}]}]}}, newMeta = MaterializedContainerNode{version=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@f241520, subtreeVersion=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@113c7c9a, children={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry=LazyContainerNode{version=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@f241520, subtreeVersion=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@113c7c9a, children={(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=deny1}]=SimpleContainerNode{version=org.opendaylight.yangtools.yang.data.api.schema.tree.spi.Version@113c7c9a, data=ImmutableMapEntryNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=deny1}], value=[ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)actions, value=[ImmutableChoiceNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)packet-handling, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)deny, value=empty}]}]}, ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)matches, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)source-ipv4-network, value=3.4.5.6/24}]}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name, value=deny1}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)node-id, value=openflow:1}]}}}, untouched=[ImmutableMapEntryNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)access-list-entry[{(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name=permit1}], value=[ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)actions, value=[ImmutableChoiceNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)packet-handling, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)permit, value=empty}]}]}, ImmutableContainerNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)matches, value=[ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)source-ipv4-network, value=10.0.0.1/32}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)destination-ipv4-network, value=10.0.0.2/32}]}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)rule-name, value=permit1}, ImmutableLeafNode{identifier=(urn:opendaylight:params:xml:ns:yang:tutorial3?revision=2019-11-19)node-id, value=openflow:1}]}]}}}}}}] 
```

A consulta não gera informações no log.
