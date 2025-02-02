package mage.cards.e;

import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ExileIntoDarkness extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ExileIntoDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Target player sacrifices a creature with converted mana cost 3 or less.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // At the beginning of your upkeep, if you have more cards in hand than each opponent, you may return Exile into Darkness from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                TargetController.YOU, new ReturnSourceFromGraveyardToHandEffect(),
                true).withInterveningIf(MoreCardsInHandThanOpponentsCondition.instance));
    }

    private ExileIntoDarkness(final ExileIntoDarkness card) {
        super(card);
    }

    @Override
    public ExileIntoDarkness copy() {
        return new ExileIntoDarkness(this);
    }
}
