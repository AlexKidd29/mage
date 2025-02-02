package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Quercitron
 */
public final class GoblinWarrens extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "Goblins");
    
    public GoblinWarrens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // {2}{R}, Sacrifice two Goblins: Create three 1/1 red Goblin creature tokens.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new GoblinToken(), 3), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(2, filter));
        this.addAbility(ability);
    }

    private GoblinWarrens(final GoblinWarrens card) {
        super(card);
    }

    @Override
    public GoblinWarrens copy() {
        return new GoblinWarrens(this);
    }
}
