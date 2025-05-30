package mage.cards.k;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KamiOfTheCrescentMoon extends CardImpl {

    public KamiOfTheCrescentMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of each player's draw step, that player draws an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(TargetController.EACH_PLAYER, new DrawCardTargetEffect(1).setText("that player draws an additional card"),
                false));
    }

    private KamiOfTheCrescentMoon(final KamiOfTheCrescentMoon card) {
        super(card);
    }

    @Override
    public KamiOfTheCrescentMoon copy() {
        return new KamiOfTheCrescentMoon(this);
    }
}
