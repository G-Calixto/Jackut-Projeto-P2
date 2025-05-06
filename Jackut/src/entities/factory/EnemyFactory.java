package entities.factory;

import entities.relationship.AbstractRelationship;
import entities.relationship.Enemy;

public class EnemyFactory implements RelationshipFactory {
    @Override
    public AbstractRelationship createRelationship() {
        return new Enemy();
    }
} 