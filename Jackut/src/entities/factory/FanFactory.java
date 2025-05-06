package entities.factory;

import entities.relationship.AbstractRelationship;
import entities.relationship.Fan;

public class FanFactory implements RelationshipFactory {
    @Override
    public AbstractRelationship createRelationship() {
        return new Fan();
    }
} 