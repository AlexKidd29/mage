
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlvenwaldCaptive extends CardImpl {

    public UlvenwaldCaptive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.u.UlvenwaldAbomination.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {5}{G}{G}: Transform Ulvenwald Captive.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private UlvenwaldCaptive(final UlvenwaldCaptive card) {
        super(card);
    }

    @Override
    public UlvenwaldCaptive copy() {
        return new UlvenwaldCaptive(this);
    }
}
