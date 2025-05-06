package entities.factory;

import entities.relationship.AbstractRelationship;
import entities.relationship.Friendship;

public class FriendshipFactory implements RelationshipFactory {
    @Override
    public AbstractRelationship createRelationship() {
        return new Friendship();
    }
} 