package entities.factory;

import entities.relationship.AbstractRelationship;

public class RelationshipFactoryProvider {
    private static final FriendshipFactory friendshipFactory = new FriendshipFactory();
    private static final FanFactory fanFactory = new FanFactory();
    private static final EnemyFactory enemyFactory = new EnemyFactory();

    public static AbstractRelationship createRelationship(String type) {
        switch (type.toLowerCase()) {
            case "amigo":
                return friendshipFactory.createRelationship();
            case "fa":
                return fanFactory.createRelationship();
            case "inimigo":
                return enemyFactory.createRelationship();
            default:
                throw new IllegalArgumentException("Tipo de relacionamento não suportado: " + type);
        }
    }
} 